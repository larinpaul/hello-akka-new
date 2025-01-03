package section05playingwithremoteactors

import akka.actor.{Actor, ActorRef, ActorSystem, Props, RootActorPath, Terminated}
import com.typesafe.config.ConfigFactory

import scala.util.Random

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

object Backend {
  def initiate(port: Int) = {
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
      withFallback(ConfigFactory.load().getConfig("Backend"))

    val system = ActorSystem("ClusterSystem", config)

    val Backend = system.actorOf(Props[Backend], name = "Backend")
  }
}


// package com.packt.akka.cluster

// import ...

class Frontend extends Actor {

  var backends = IndexedSeq.empty[ActorRef]

  def receive = {
    case Add if backends.isEmpty =>
      println("Service unavailable, cluster doesn't have backend node.")
      backends(Random.nextInt(backends.size)) forward addOp
    case BackendRegistration if !(backends.contains(sender())) =>
      backends = backends :+ sender()
      context watch(sender())
    case Terminated(a) =>
      backends = backends.filterNot(_ == a)

  }

}

object Frontend {

  private var _frontend: ActorRef = _

  def initiate() = {
    val config = ConfigFactory.load().getConfig("Frontend")
    val system = ActorSystem("ClusterSystem", config)
    _frontend = system.actorOf(Props[Frontend], name = "frontend")
  }

  def getFrontend = _frontend
}


