package section05playingwithremoteactors

import akka.actor.Nobody.path
import akka.actor.{ActorPath, ActorSystem, PoisonPill, Props}
import com.typesafe.config.ConfigFactory

class ClusterApp extends App {

  //initiate frontend node
  Frontend.initiate()

  // initiate three nodes from backend
  Backend.initiate(2552)

  Backend.initiate(2560)

  Backend.initiate(2561)

  Thread.sleep(10000)

  Frontend.getFrontend ! Add(2, 4)

}

object SingletonApp extends App {

  startup(Seq("2551", "2552", "0"))

  def startup(ports: Seq[String]): Unit = {
    ports foreach { port =>

      // Override the configuration of the port
      val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
        withFallback(ConfigFactory.load("singleton"))

      // Create an Akka system
      val system = ActorSystem("ClusterSystem", config)

      startupSharedJournal(system startStore = (port == "2551"), path =
        ActorPath.fromString("akka.tcp://ClusterSystem@127.0.0.1:2551/user/store"))

      val master = system.actorOf(ClusterSingletonManager.props(
        SingletonProps = Props[Master],
        terminationMessage = PoisonPill,
        settings = ClusterSingletonManagerSettings(system).withRole(None)
      ), name = "master")

      Cluster(system) registerOnMemberUp {
        system.actorOf(Worker.props, name = "worker")
      }

      // ...

    }
  }

}

