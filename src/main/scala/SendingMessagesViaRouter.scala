class SendingMessagesViaRouter {

}

// Part 3.2

// Sending Messages via Router

// * How to implement Router Actor
// * Router types
// * Implementing some Akka routing strategies
// * Introducing other router strategies


// Let's look at the Worker actor,
// which only handles work messages

// package com.packt.akka

import akka.actor.Actor

class Worker extends Actor {
  import Worker._

  def receive = {
    case msg: Work =>
      println(s"I received Work Message and My ActorRef: ${self}")
  }
}

object Worker {
  case class Work()
}
