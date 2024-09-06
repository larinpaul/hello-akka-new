class ActorRefVsActorPathVsActroSelection {

  // * Actor Ref versus Actor Path versus Actor Selection
  // * Routing
  // * Replacing actor stack behavior via become/unbecome
  // * Replacing actor stack behavior via FSM

  // When the actor restarts, the instance still points to the same ActorRef
  // but then point to a DeadLetter

  // Actors are created in a strictly hierarchical fashion

  // My Path:
  // /user/Actor A/Actor B

  // Actor Path represents the name of the actor
  // So that you can delete and create an actor with the same path
  // It will have the same path but a different actor reference

  // Actor Selection is another representation of the actor like the actor reference
  // You can send messages to an actor by Actor Selection, like Actor Ref
  // The main difference is, Actor Selection
  // is created from Actor Path or Actor Name
  // It stays valid even when an actor dies
  // and another instance is created
  // Unlike ActorRef, it doesn't represent an instance,
  // it represents a path,
  // so you can't watch it!

}

// we have a Counter actor that handles two messages

import akka.actor.{ ActorRef, ActorSystem, Props, Actor }

class Counter extends Actor {
  import Counter._

  var count = 0

  def receive = {
    case Inc(x) =>
      count += x
    case Dec(x) =>
      count -= x
  }

}

object Counter {

  final case class Inc(num: Int)
  final case class Dec(num: Int)
}

