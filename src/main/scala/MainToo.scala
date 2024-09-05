// package com.packt.akka

import UserStorage.{Connect, Operation}
import akka.actor.{Actor, ActorRef, ActorSystem, Props, Stash}
import akka.io.UdpConnected.Disconnect

case class User(username: String, email: String)

object UserStorage {

  trait DBOperation
  object DBOperation {
    case object Create extends DBOperation
    case object Update extends DBOperation
    case object Read extends DBOperation
    case object Delete extends DBOperation
  }

  case object Connect
  case object DisConnect
  case class Operation(dBOperation: DBOperation, user: Option[User])

}

class UserStorage extends Actor {

  def receive = disconnected

  def connected: Actor.Receive = {
    case Disconnect =>
      println("User Storage Disconnect from DB")
      context.unbecome()
    case Operation(op, user) =>
      println(s"User Storage receive ${op} to do in user: ${user}")
  }

  def disconnected: Actor.Receive = {
    case Connect =>
        println(s"User Storage connected to DB")
        context.become(connected)
  }

}

object BecomeHotswap extends App {
  import UserStorage._

  val system = ActorSystem("Hotswap-Become")

  val userStorage = system.actorOf(Props[UserStorage], "userStorage")

  userStorage ! Connect

  userStorage ! Operation(DBOperation.Create, Some(User("Admin", "admin@packt.com")))

  userStorage ! Disconnect

  Thread.sleep(100)

  system.shutdown()
}

