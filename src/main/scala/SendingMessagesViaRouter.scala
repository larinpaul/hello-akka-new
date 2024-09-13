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

import akka.actor.{Actor, ActorRef, Props}

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


// Implementation of the Router actor

class Router extends Actor {

  var routees: List[ActorRef] = _

  override def preStart() = {
    routees = List.fill(5)(
      context.actorOf(Props[Worker])
    )
  }

  def receive() = {
    case msg: Work =>
      println("I'm A Router and I received a Message.....")
      routees(util.Random.nextInt(routees.size)) forward msg
  }

}
// Forward message means that the original sender reference is maintained
// even though a message is going through a mediator, like a Rooter

class RouterGroup(routees: List[String]) extends Actor {
  case msg: Work =>
    println(s"I'm a Router Group and I receive Work Message....")
    context.actorSelection(routees(util.Random.nextInt(routees.size))) forward msg
}

