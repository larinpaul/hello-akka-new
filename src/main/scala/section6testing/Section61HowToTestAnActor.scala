package section6testing

class Section61HowToTestAnActor {

  // Akka has a set of modules for testing, the
  // Akka Test Kit

}

// package com.packt.akka

import akka.actor.{ Actor, ActorRef, ActorSystem, Props }

class Counter extends Actor {
  import Counter._

  var count: Int = 0
  def receive = {
    case Increment =>
      count += 1
    case Decrement =>
      count -= 1
    case GetCount =>
      sender ! count
  }
}

object Counter {
  case object Increment
  case object Decrement
  case object GetCount
}
