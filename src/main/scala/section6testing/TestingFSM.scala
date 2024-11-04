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

import akka.actor.{ActorSystem, FSM, Props, Stash}
import akka.io.UdpConnected.{Connect, Connected, Disconnect, Disconnected}

import scala.Console.in

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

// package com.packt.akka // test

// import ...

class UserStorageSpec extends TestKit(ActorSystem("test-system"))
                      with ImplicitSender
                      with FlatSpecLike
                      with BeforeAndAfterAll
                      with MustMatchers {
  import UserStorageFSM._

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "User Storage" should "Start with disconnected state and empty data" in {
    val storage = TestFSMRef(new UserStorageFSM())

    storage.stateName must equal(Disconnected)
    storage.stateData must equal(EmptyData)

  }

  it should be "connected state if it receive a connect message" in {
    val storage = TestFSMRef(new UserStorageFSM())

    storage ! Connect

    storage.stateName must equal(Connected)
    storage.stateData must equal(EmptyData)
  }


}

