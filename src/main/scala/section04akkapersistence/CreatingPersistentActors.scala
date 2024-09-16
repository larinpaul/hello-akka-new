package section04akkapersistence

import akka.actor.ActorLogging

class CreatingPersistentActors {

  // Part 4.2
  // Creating Persistent Actors
  // * Define Counter Actor
  // * Implement Counter Actor

}

// package com.packt.akka

object Counter {
  sealed trait Operation {
    val count: Int
  }

  case class Increment(override val count: Int) extends Operation
  case class Decrement(override val count: Int) extends Operation

  case class Cmd(op: Operation)
  case class Evt(op: Operation)

  case class State(count: Int)

}

class Counter extends PersistentActor with ActorLogging {
  import Counter._

  println("Starting ........................")

  // Persistent Identifier
  override def persistenceId = "counter-example"

  val state

  // Persistent receive on recovery mood

  // Persistent receive on normal mood

}
