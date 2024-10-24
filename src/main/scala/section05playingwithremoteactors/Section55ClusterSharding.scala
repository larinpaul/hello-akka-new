package section05playingwithremoteactors

import Counter.Inc
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.actor.SupervisorStrategy.Stop
import akka.persistence.PersistentActor
import section05playingwithremoteactors.Frontend.Tick

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

  val counterRegion: ActorRef = ???

  def receive = {
    case Tick(Inc) =>
      log.info(s"Frontend: Send Increment message to shard region.")
      counterRegion ! Counter.CounterMessage(getId, Counter.Increment)
    case Tick(Dec) =>
      log.info(s"Frontend: Send Decrement message to shard region.")
      ounterRegion ! Counter.CounterMessage(getId, Counter.Decrement)
    case Tick(Get) =>
      log.info(s"Frontend: Send Get message to shard region.")
      counterRegion ! Counter.CounterMessage(getId, Counter.Get)
  }

  def getId = Random.nextInt(4)
}
