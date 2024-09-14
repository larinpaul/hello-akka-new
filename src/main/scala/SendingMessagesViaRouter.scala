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

import Worker.Work
import akka.actor.{Actor, ActorRef, Props}
import akka.routing.FromConfig

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

import scala.util

import akka.actor.{ Actor, ActorRef, ActorSystem, Props }
import Worker._

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

  def receive = {
    case msg: Work =>
      println(s"I'm a Router Group and I receive Work Message....")
      context.actorSelection(routees(util.Random.nextInt(routees.size))) forward msg
  }

}


// App.scala

import akka.actor.{Props, ActorSystem}
//import com.packt.akka.Worker.Work


object RouterApp extends App {

  val system = ActorSystem("router")

  system.actorOf(Props[Worker], "w1")
  system.actorOf(Props[Worker], "w2")
  system.actorOf(Props[Worker], "w3")
  system.actorOf(Props[Worker], "w4")
  system.actorOf(Props[Worker], "w5")

  val workers: List[String] = List(
    "user/w1",
    "user/w2",
    "user/w3",
    "user/w4",
    "user/w5")
  val routerGroup = system.actorOf(Props(classOf[RouterGroup], workers))

  routerGroup ! Work()

  routerGroup ! Work()

  Thread.sleep(100)

  system.terminate()

}
// Good... Our implementation is good...
// But this will create a bottleneck in our app...


// package com.packt.akka

// import ...

object Random extends App {

  val system = ActorSystem("Random-Router")
  val routerPool = system.actorOf(FromConfig.props(Props[Worker]), "random-router-pool")
  routerPool ! Work()
  routerPool ! Work()
  routerPool ! Work()
  routerPool ! Work()
  Thread.sleep(100)
  system.terminate()

}

