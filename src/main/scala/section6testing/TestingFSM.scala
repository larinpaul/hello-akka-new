package section6testing

class TestingFSM {

  // Testing FSM... The Finite State Machines!

  // In this chapter, we are going to take a look at...
  // * Remembering user storage
  // * How to test FSM Actor

  // Testing an Actor should be done via messaging...
  // However, in a FSM, it is sometimes useful to drop down
  // to use FSM actor ref
  // It is also unsafe in the same way as the tester actor ref is...
  // However, let's see which additional tools it provides...

}

// package com.packt.akka

import UserStorage.Operation
import akka.actor.{ActorSystem, FSM, Props, Stash}
import akka.io.UdpConnected.{Connect, Connected, Disconnect, Disconnected}

class UserStorageFSM extends FSM[UserStorageFSM.State, UserStorageFSM.Data] with Stash {
  import UserStorageFSM._

  startWith(Disconnected, EmptyData)

  when(Disconnected) {
    case Event(Connect, _) =>
      println("UserSotrage connected to DB")
      unstashAll()
      goto(Connected) using EmptyData

    case Event(msg, _) =>
      stash()
      stay using EmptyData
  }

  when(Connected) {
    case Event(Disconnect, _) =>
      println("UserStorage disconnected from DB")
      goto(Disconnected) using EmptyData

    case Event(Operation(op, user), _) =>
      println(s"UserStorage receive ${op} operation to do in user: ${user}")
      stay using EmptyData
  }

  initialize()

}


