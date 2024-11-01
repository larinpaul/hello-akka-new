package section6testing

class Section62TestingAParentChildRelationship {

  // In this section, we are going to look at...
  // * Testing parent actor
  // * Testing child actor

}

// package com.packt.akka

import akka.actor.Actor

class Child extends Actor {
  def receive = {
    case "ping" => context.parent ! "pong"
  }
}


