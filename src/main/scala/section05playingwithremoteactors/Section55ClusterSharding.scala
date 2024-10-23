package section05playingwithremoteactors

import akka.actor.ActorLogging
import akka.actor.SupervisorStrategy.Stop
import akka.persistence.PersistentActor

import scala.concurrent.duration.DurationInt

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
  import section04akkapersistence.Counter._

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
