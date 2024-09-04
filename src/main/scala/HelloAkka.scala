class HelloAkka {

}

// Define Actor Messages
case class WhoToGreet(who: String)

// Define Greeter Actor
// our receive function is a parcial function that takes anything and returns a unit
// As we have a single message, we'll have a single case

object HelloAkkScala extends App {

  // Create the 'hello akka' actor system

  // Create the 'greeter' actor

  // Send WhoToGreet Message to actor

}


