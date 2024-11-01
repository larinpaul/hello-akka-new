package section6testing

class Section62TestingAParentChildRelationship {

  // In this section, we are going to look at...
  // * Testing parent actor
  // * Testing child actor

}

// package com.packt.akka

import akka.actor.Actor

class Child extends Actor { // When it receives the "ping" message, it sends a "pong" to its parent
  def receive = {
    case "ping" => context.parent ! "pong"
  }
}

// package com.packt.akka

import akka.actor.{ Actor, Props }

class Parent extends Actor {
  val child = context.actorOf(Props[Child])
  var ponged = false

  def receive = {
    case "ping" => child ! "ping"
    case "pong" => ponged = true
  }
}


