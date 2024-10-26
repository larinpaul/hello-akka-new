import akka.actor.{ActorSystem, Props}
// package com.packt.akka

// import ...

class CounterSpec extends TestKit(ActorSystem("test-system"))
                  with FlatSpecLike
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
}

