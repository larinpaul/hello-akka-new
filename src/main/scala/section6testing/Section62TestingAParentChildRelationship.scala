package section6testing

class Section62TestingAParentChildRelationship {

  // In this section, we are going to look at...
  // * Testing parent actor
  // * Testing child actor

}

// package com.packt.akka

import akka.actor.{Actor, ActorRef, ActorRefFactory}

class Child(parent: ActorRef) extends Actor { // When it receives the "ping" message, it sends a "pong" to its parent
  def receive = {
    case "ping" => parent ! "pong"
  }
}

// package com.packt.akka

import akka.actor.{ Actor, Props }

class Parent(childMaker: ActorRefFactory => ActorRef) extends Actor {
  val child = childMaker(context)
  var ponged = false

  def receive = {
    case "ping" => child ! "ping"
    case "pong" => ponged = true
  }
}


