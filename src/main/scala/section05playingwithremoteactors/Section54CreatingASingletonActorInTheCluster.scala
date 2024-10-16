package section05playingwithremoteactors

import akka.actor.ActorRef

class Section54CreatingASingletonActorInTheCluster {

  // We are going to look at:
  // * Cluster Singleton
  // * How it works
  // * Implementing Singleton Actor

  // Use Cases to Use Singleton
  // * Single point of responsibility for important decisions in the cluster
  // * Single entry point to an external
  // * Single master, many workers
  // * Centralized naming service or routing logic

  // Singleton should not be your first design choice since it has drawbacks...
  // Drawbacks:
  // * Cluster Singleton may be a bottleneck
  // * Cluster Singleton can not be available all the time

}

// package com.packt.akka.cluster.singleton

// import ...

object Master {

  trait Command
  case class RegisterWorker(worker: ActorRef) extends Command
  case class RequestWork(requester: ActorRef) extends Command
  case class Work(requester: ActorRef, op: String) extends Command

  trait Event
  case class AddWorker(worker: ActorRef) extends Event
  case class AddWork(work: Work) extends Event
  case class UpdateWorks(works: List[Work]) extends Event

  case class State(workers: Set[ActorRef], works: List[Work])

  case object NoWork
}
