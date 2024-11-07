package section6testing

class Section64MultiNodeTesting {

  // Part 6.4 Multi Node Testing
  // * Introduction to Multi Node testing
  // * Parts of Multi Node test kit
  // * Implementing an example
  // * Multi JVM testing (same machine, multiple JVM)
  // * Multi Node testing (many JVMs, many machines)
  // * But the API is basically the same

}

// package com.packt.akka

import akka.actor.{ Actor, ActorRef, ActorSystem, Props }

class Worker extends Actor {
  import Worker._

  def receive = {
    case Work =>
      println(s"I received Work Message and My ActorRef: ${self}")
      sender() ! Done
  }
}

object Worker {
  case object Work
  case object Done
}
