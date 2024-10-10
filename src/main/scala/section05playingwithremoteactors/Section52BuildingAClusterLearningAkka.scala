package section05playingwithremoteactors

import akka.actor.{Actor, RootActorPath}

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

  val cluster = Cluster(context.system)

  // subscribe to cluster changes, MemberUp
  // re-subscribe when restart
  override def preStart(): Unit = {
    cluster.subscribe(self, classOf[MemberUp])
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  def receive = {
    case Add(num1, num2) =>
      println(s"I'm a backend with path: ${self} and I received add operation.")
    case MemberUp(member) =>
      if(member.hasRole("frontend")){
        context.actorSelection(RootActorPath(member.address) / "user" / "frontend") !
          BackendRegistration
      }
  }

}

object Backend {}

