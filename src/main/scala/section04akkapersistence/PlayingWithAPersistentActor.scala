package section04akkapersistence

import akka.actor.Actor.Receive
import akka.persistence.SnapshotOffer
import akka.persistence.fsm.PersistentFSM.State

class PlayingWithAPersistentActor {

}

object ReceiveCommand {

  val receiveCommand: Receive = {
    case cmd @ Cmd(op) =>
      println(s"Countr receive ${cmd}")
      persist(Evt(op)) { evt =>
        updateState(evt)
      }
    case "print" =>
      println(s"The Current state of counter is ${state}")
  }

}

// New Version of Persist
// * persistAsync

object ReceuveReciver {

  val receiveRecover: Receive = {
    case evt: Evt =>
      println(s"Countr receive ${evt} on recovering mood")
      updateState(evt)

    case SnapshotOffer(_, snapshot: State) =>
      println(s"Countr receive snapshot with data: {snapshot} on recovering mood")
      state = snapshot
  }

}

