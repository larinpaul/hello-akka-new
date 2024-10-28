import akka.actor.{ActorSystem, Props}

import scala.Console.in
import scala.concurrent.duration.DurationInt
// package com.packt.akka

// import ...

class CounterSpec extends TestKit(ActorSystem("test-system"))
                  with FlatSpecLike
                  with ImplicitSender // makes the test kit able to receive messages directly
                  with BeforeAndAfterAll
                  with MustMatchers {
  override def afterAll = {
    TestKit.shutdownActorSystem(system)
  }

  "Counter Actor" should "handle GetCount message with using TestProbe" in {
    val sender = TestProbe()

    val counter = system.actorOf(Props[Counter])

    sender.send(counter, Counter.Increment)

    sender.send(counter, Counter.GetCount)

    val state = sender.expectMsgType[Int]

    state must equal(1)

  }

  it should "handle Increment message" in { // let's define a running instance
    val counter = system.actorOf(Props[Counter])

    counter ! Counter.Increment

    expectNoMsg(1.second)
  }

  it should "handle Decrement message" in {
    val counter = system.actorOf(Props[Counter])

    counter ! Counter.Decrement

    expectNoMsg(1.second)
  }

}

