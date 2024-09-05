object Main {
  def main(args: Array[String]): Unit = {

    println("Hello world!")

    // To create a Hello World App, We Need to...

    // * Create Greet message
    // * Create Greeter actor
    // * Send Greet message to your Greeter actor

    // Part 1.3 Concepts and Terminology Learning Akka
    // * Concurrency and Parallelism
    // * Asynchronous versus Synchronous
    // * Non-blocking versus Blocking
    // * Race condition



  }
}


import akka.actor.{Actor, ActorSystem, Props}

class HelloAkka {

}

// Define Actor Messages
case class WhoToGreet(who: String)

// Define Greeter Actor
// our receive function is a parcial function that takes anything and returns a unit
// As we have a single message, we'll have a single case
class Greeter extends Actor {
  def receive = {
    case WhoToGreet(who) => println(s"Hello $who")
  }
}

// The main method must be a static method.
// In Scala to create a "static" (actually singleton) method
// you put it in an object
// Methods in a class are not static
object HelloAkkaScala extends App {

  // Create the 'hello akka' actor system
  val system = ActorSystem("Hello-Akka")

  // Create the 'greeter' actor
  val greeter = system.actorOf(Props[Greeter], "greeter")

  // Send WhoToGreet Message to actor
  greeter ! WhoToGreet("Akka")

}