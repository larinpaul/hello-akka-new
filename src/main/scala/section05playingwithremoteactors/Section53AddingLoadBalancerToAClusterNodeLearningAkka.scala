package section05playingwithremoteactors

import akka.actor.Actor
import akka.routing.FromConfig

import scala.concurrent.duration.DurationInt
import scala.util.Random

class Section53AddingLoadBalancerToAClusterNodeLearningAkka {

  // We will take a look at:
  // * Cluster metrics
  // * Introduction to load balancer
  // * Add load balancer to our cluster

  // In our cluster we had:
  // 1 frontend node
  // 3 backend nodes

  // Cluster metrics report the system status back to Akka
  // Cluster metrics contains data about
  // * Heap memory
  // * System load for the last minute
  // * CPU utilization

}

// package com.packt.akka.loadBalancing
// import ...

class Frontend extends Actor {
  import context.dispatcher

  val backend = context.actorOf(FromConfig.props(), name = "backendRouter")

  context.system.scheduler.schedule(3.seconds, 3.seconds, self,
    Add(Random.nextInt(100), Random.nextInt(100)))

  def receive = {
    case addOp: Add =>
        println("Frontend: I'll forward add operation to backend node to handle it.")
      backend forward addOp
  }

}


