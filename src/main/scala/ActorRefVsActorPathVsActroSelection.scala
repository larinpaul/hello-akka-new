class ActorRefVsActorPathVsActroSelection {

  // * Actor Ref versus Actor Path versus Actor Selection
  // * Routing
  // * Replacing actor stack behavior via become/unbecome
  // * Replacing actor stack behavior via FSM

  // When the actor restarts, the instance still points to the same ActorRef
  // but then point to a DeadLetter

  // Actors are created in a strictly hierarchical fashion

  // My Path:
  // /user/Actor A/Actor B

  // Actor Path represents the name of the actor
  // So that you can delete and create an actor with the same path
  // It will have the same path but a different actor reference

  // Actor Selection is another representation of the actor like the actor reference
  // You can send messages to an actor by Actor Selection, like Actor Ref
  // The main difference is, Actor Selection
  // is created from Actor Path or Actor Name
  // It stays valid even when an actor dies
  // and another instance is created
  // Unlike ActorRef, it doesn't represent an instance,
  // it represents a path,
  // so you can't watch it!

}

// we have a Counter actor that handles two messages

import akka.actor.{ ActorRef, ActorSystem, Props, Actor }

class Counter extends Actor {
  import Counter._

  var count = 0

  def receive = {
    case Inc(x) =>
      count += x
    case Dec(x) =>
      count -= x
  }

}

object Counter {

  final case class Inc(num: Int)
  final case class Dec(num: Int)
}

// App.scala

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, PoisonPill }

object ActorPath extends App {

  val system = ActorSystem("Actor-Paths")
  val counter1 = system.actorOf(Props[Counter], "Counter")
  println(s"Actor Reference for counter1: ${counter1}")
  val counterSelection1 = system.actorSelection("counter")
  println(s"Actor Selection for counter1: ${counterSelection1}")
  counter1 ! PoisonPill
  Thread.sleep(100)
  val counter2 = system.actorOf(Props[Counter], "Counter")
  println(s"Actor Reference for count2 is: ${counter2}")
  val counterSelection2 = system.actorSelection(("counter"))
  println(s"Actor Selection fro counter2 is: ${counterSelection2}")
  system.terminate()

}

// package com.packt.akka

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Identify, ActorIdentity }

class Watcher extends Actor {

  var counterRef: ActorRef = _
  val selection = context.actorSelection("/user/counter")
  selection ! Identify(None)
  def receive = {
    case ActorIdentity(_, Some(ref)) =>
      println(s"Actor Reference for counter is ${ref}")
    case ActorIdentity(_, None) =>
      println("Actor selection for actor doesn't live : (")
  }


}


// Now let's create two instance forms, counter and watcher

// package com.packt.akka

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, PoisonPill }

object Watch extends App {

  val system = ActorSystem("Watch-actor-selection")
  val counter = system.actorOf(Props[Counter], "counter")
  val watcher = system.actorOf(Props[Watcher], "watcher")
  system.terminate()

}

