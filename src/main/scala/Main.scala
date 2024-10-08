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

    // Concurrency is when two tasks may start, run, and complete with overlapping time periods
    // (Multitasking on a single core machine)
    // Parallelism is when tasks literally run at the same time
    // (Multithreads on Multicore processor)

    // A method call is synchronous if the caller cannot make progress until the method returns a value or throws an exception
    // A method call is asynchronous if the caller can make progress after a finite number of steps, and the completion of the method may be signaled via some additional mechanism
    // (callback, future or message)
    // Actors are asynchronous by nature

    // Blocking is when if the delay of one thread can indefinitely delay some of the other threads
    // On another hand, Non-blocking means that no thread is able to indefinitely delay others

    // Race condition is when multiple threads have a shared mutable state and if one or more of the threads try to change the state at the same time
    // Actor has one message, so there could be no race condition inside the actor




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