object MainToo {

  def main(args: Array[String]): Unit = {

    // Part 3.3. Replacing Actor Behavior via become/unbecome

    // * How to change actor behavior at runtime by become and unbecome
    // * Stash messages

    // We will first implement UserStorage Actor
    // Out Actor should be able to:
    // * Connect to database
    // * Disconnect from database
    // * CRUD operations
  }

}

// package com.packt.akka

import akka.actor.{ Actor, ActorRef, ActorSystem, Props, Stash }

case class User(username: String, email: String)

object UserStorage {

  trait DBOpearation
  object DBOpearation {
    case object Create extends DBOpearation
    case object Update extends DBOpearation
    case object Read extends DBOpearation
    case object Delete extends DBOpearation
  }

}


