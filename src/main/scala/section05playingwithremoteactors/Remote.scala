package section05playingwithremoteactors

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Remote {



}

// package com.packt.akka

// import ...

object MembersService extends App {
  val config = ConfigFactory.load.getConfig("MembersService")

  val system = ActorSystem("MemberService", config)

  val worker = system.actorOf(Props[Worker], "remote-worker")
  println(s"Worker actor path is ${worker.path}")
}

