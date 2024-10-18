package section05playingwithremoteactors

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.persistence.PersistentActor

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

class Master extends PersistentActor with ActorLogging {
  import Master._

  var workers: Set[ActorRef] = Set.empty
  var works: List[Work] = List.empty

  override def persistenceId: String = self.path.parent.name + "-" + self.path.name

  def updateState(evt: Event): Unit = evt match {...}

  val receiveRecover: Receive = {...}

  val receiveCommand: Receive = {
    case RegisterWorker(worker) =>
      persist(AddWorker(worker)) { evt =>
        updateState(evt)
      }

    case RequestWork if works.isEmpty =>
      sender() ! NoWork

    case RequestWork(requester) if workers.contains(requester) =>
      sender() ! works.head
      persist(UpdateWorks(works.tail)) { evt =>
        updateState(evt)
      }

    case w: Work =>
      persist(AddWork(w)) { evt =>
        updateState(evt)
      }
  }

}

// package com.packt.akka.cluster.singleton

// import ...

class Worker extends Actor with ActorLogging {


}


