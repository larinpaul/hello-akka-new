package section05playingwithremoteactors

import akka.actor.Actor

class Section52BuildingAClusterLearningAkka {

  // Akka Cluster
  // * Akka Cluster provides a fault-tolerant decentralized peer-to-peer cluster
  // membership service with no single point of failure or single point of bottleneck
  // * Akka Cluster does this using gossip protocol and an automatic failure detector

  // Membership Lifecycle
  // Joining -> Up -> Leaving -> Existing -> Removed
  //            |                              A
  //            -> Existing -> Down -----------|

  // We will have a frontend and a backend node
  //

}

// package com.packt.akka.cluster

// import ...

class Backend extends Actor {

  val cluster = ???

  // subscribe to cluster changes, MemberUp
  // re-subscribe when restart
  override def preStart(): Unit = ???

  override def postStop(): Unit = ???

  def receive = {
    case Add(num1, num2) =>
      println(s"I'm a backend with path: ${self} and I received add operation.")
  }

}

object Backend {}

