//package section04akkapersistence
//
//import akka.actor.Actor.Receive
//import akka.actor.{ActorLogging, ActorSystem, Props}
//import akka.persistence.SnapshotOffer
//
//class CreatingPersistentActors {
//
//  // Part 4.2
//  // Creating Persistent Actors
//  // * Define Counter Actor
//  // * Implement Counter Actor
//
//}
//
//// package com.packt.akka
//
//object Counter {
//  sealed trait Operation {
//    val count: Int
//  }
//
//  case class Increment(override val count: Int) extends Operation
//  case class Decrement(override val count: Int) extends Operation
//
//  case class Cmd(op: Operation)
//  case class Evt(op: Operation)
//
//  case class State(count: Int)
//
//}
//
//object Counter {
//  sealed trait Operation {
//    val count: Int
//  }
//
//  case class Increment(override val count: Int) extends Operation
//  case class Decrement(override val count: Int) extends Operation
//
//  case class Cmd(op: Operation)
//  case class Evt(op: Operation)
//
//  case class State(count: Int)
//}
//
//class Counter extends PersistentActor with ActorLogging {
//  import Counter._
//
//  println("Starting ........................")
//
//  // Persistent Identifier
//  override def persistenceId: String = "counter-example"
//
//  var state: State = State(count = 0)
//
//  def updateState(evt: Evt): Unit = evt match {
//    case Evt(Increment(count)) =>
//      state = State(count = state.count + count)
//    case Evt(Decrement(count)) =>
//      state = State(count = state.count - count)
//  }
//
//  // Persistent receive on recovery mood
//  override def receiveRecover: Receive = {
//    case evt: Evt =>
//      println(s"Counter receive ${evt} on recovering mood")
//      updateState(evt)
//    case SnapshotOffer(_, snapshot: State) =>
//      println(s"Counter receive snapshot with data: ${snapshot} on recovering mood")
//      state = snapshot
//  }
//
//  // Persistent receive on normal mood
//  override def receiveCommand: Receive = {
//    case cmd @ Cmd(op) =>
//      println(s"Counter receive ${cmd}")
//      persist(Evt(op)) { evt =>
//        updateState(evt)
//      }
//
//    case "print" =>
//      println(s"The Current state of counter is ${state}")
//  }
//}
//
//class Counter extends PersistentActor with ActorLogging {
//  import Counter._
//
//  println("Starting ........................")
//
//  // Persistent Identifier
//  override def persistenceId = "counter-example"
//
//  var state: State = State(count= 0)
//
//  def updateState(evt: Evt): Unit = evt match {
//    case Evt(Increment(count)) =>
//        state = State(count = state.count + count)
//    case Evt(Decrement(count)) =>
//      state = State(count = state.count - count)
//  }
//
//  // Persistent receive on recovery mood
//  val receiveRecover: Receive = {
//    case evt: Evt =>
//      println(s"Counter receive ${evt} on recovering mood")
//      updateState(evt)
//    case SnapshotOffer(_, snapshot: State) =>
//      println(s"Counter receive snapshot with data: ${snapshot} on recovering mood")
//      state = snapshot
//  }
//
//  // Persistent receive on normal mood
//  val receiveCommand: Receive = {
//    case cmd @ Cmd(op) =>
//      println(s"Counter receive ${cmd}")
//      persist(Evt(op)) { evt =>
//        updateState(evt)
//      }
//
//    case "print" =>
//      println(s"The Current state of counter is ${state}")
//  }
//
//}
//
//// package com.pack.akka
//
//
//
//object Persistent extends App {
//  import Counter._
//
//  val system = ActorSystem("persistent-actors")
//
//  val counter = system.actorOf(Props[Counter])
//
//  counter ! Cmd(Increment(3))
//  counter ! Cmd(Increment(5))
//  counter ! Cmd(Decrement(3))
//  counter ! "print"
//  Thread.sleep(1000)
//  system.terminate()
//
//}
