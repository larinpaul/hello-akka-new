package section05playingwithremoteactors

import Counter.Inc
import akka.actor.{Actor, ActorLogging, ActorPath, ActorRef, ActorSystem, Props}
import akka.actor.SupervisorStrategy.Stop
import akka.persistence.PersistentActor
import com.typesafe.config.ConfigFactory
import section05playingwithremoteactors.Frontend.{Dec, Get, Inc, Tick}
import section05playingwithremoteactors.SingletonApp.startup

import scala.concurrent.duration.DurationInt
import scala.util.Random

class Section55ClusterSharding {

  // A shard is a group of actors with an identifier
  // and these actors are called entries

  // Each node on a cluster involves running an actor called shard region
  // this actor is responsible for managing entries

  // Shard coordinator is a cluster singleton

}

// package com.packt.akka.cluster.sharding

// import ...

class Counter extends PersistentActor with ActorLogging {
  import Counter._

  context.setReceiveTimeout(120.seconds)

  override def persistenceId: String = self.path.parent.name + "-" + self.path.name

  var count = 0

  def updateState(event: CounterChanged): Unit =
    count += event.delta

  override def receiveRecover: Receive = {
    case evt: CounterChanged => updateState(evt)
  }

  override def receiveCommand: Receive = {
    case Increment =>
      log.info(s"Counter with path: ${self} recevied Increment Command")
      persist(CounterChanged(+1))(updateState)
    case Decrement =>
      log.info(s"Counter with path: ${self} recevied Decrement Command")
      persist(CounterChanged(-1))(updateState)
    case Get =>
      log.info(s"Counter with path: ${self} received Get Command")
      log.info(s"Count = ${count}")
      sender() ! count
    case Stop =>
      context.stop(self)
  }
}

object Counter {

  trait Command
  case object Increment extends Command
  case object Decrement extends Command
  case object Get extends Command
  case object Stop extends Command

  trait Event
  case class CounterChanged(delta: Int) extends Event

  // Sharding Name
  val shardName: String = "Counter"

  // outside world if he want to send message to sharding should use this message
  case class CounterMessage(id: Long, cmd: Command)

  // id extractor
  val idExtractor: ShardRegion.ExtractEntityId = {
    case CounterMessage(id, msg) => (id.toString, msg)
  }

  // shard resolver
  val shardResolver: ShardRegion.ExtractShardId = {
    case CounterMessage(id, msg) => (id % 12).toString
  }

  def props() = Props[Counter]

}

// Frontend actor

// package com.packt.akka.cluster.sharding

// import ...

class Frontend extends Actor with ActorLogging {
  // import ...

  val counterRegion: ActorRef = ClusterSharding(context.system).shardRegion(Counter.shardName)

  // Every 3 second will send increment operation
  context.system.scheduler.schedule(3.seconds, 3.seconds, self, Tick(Inc))

  // Every 6 second will send Decrement operation
  context.system.scheduler.schedule(6.seconds, 6.seconds, self, Tick(Dec))

  // Every 10 second will send get operation
  context.system.scheduler.schedule(10.seconds, 10.seconds, self, Tick(Get))

  def receive = {
    case Tick(Inc) =>
      log.info(s"Frontend: Send Increment message to shard region.")
      counterRegion ! Counter.CounterMessage(getId, Counter.Increment)
    case Tick(Dec) =>
      log.info(s"Frontend: Send Decrement message to shard region.")
      counterRegion ! Counter.CounterMessage(getId, Counter.Decrement)
    case Tick(Get) =>
      log.info(s"Frontend: Send Get message to shard region.")
      counterRegion ! Counter.CounterMessage(getId, Counter.Get)
  }

  def getId = Random.nextInt(4)
}

object Frontend {
  sealed trait Op
  case object Inc extends Op
  case object Dec extends Op
  case object Get extends Op

  case class Tick(op: Op)
}

// import ...

// This is to start a distributed journal

object ShardingApp extends App {

  startup(Seq("2551", "2552", "0"))

  def startup(ports: Seq[String]): Unit = {
    ports foreach { port =>
      // Override the configuration of the port
      val config = ConfigFactor.parseString("akka.remote.netty.tcp.port=" + port).
        withFallback(ConfigFactory.load("sharding"))

      // Create an Akka system
      val system = ActorSystem("ClusterSystem", config)

      startupSharedJournal(system, startStore = (port == "2551"), path =
       ActorPath.fromString("akka.tcp://ClusterSystem@127.0.0.1:2551/user/store"))

      ClusterSharding(system).start(
        typeName = Counter.shardName,
        entityProps = Counter.props(),
        settings = ClusterShardingSettings(system),
        extractEntityId = Counter.idExtractor,
        extractShardId = Counter.shardResolver)

      if (port != "2551" && port != "2552") {
        Cluster(system) registerOnMemberUp {
          system.actorOf(Props[Frontend], "frontend")
        }
      }

    }
  }

}

