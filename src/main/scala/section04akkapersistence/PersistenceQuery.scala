package section04akkapersistence

class PersistenceQuery {

  // Part 4.5
  // Persistence Query

  // * CQRS Pattern // Command Query Responsibility Segregation // Separates Read and Write operations
  // * Introduction to Persistence Query
  // * Implement system to view account logs

  // Predefined Queries

  // ** Read events for specific Persistent Actor
  // * Events-By-Persistence-Id
  // * Current-Events-By-Persistence-Id

  // * Read all events based on Tags
  // * Events-By-Tag
  // * Current-Events-By-Tag

}

// We will implement a view from our previous persistence actor

// package com.packt.akka

import akka.stream.scaladsl.Source
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import akka.persistence.query.{ PersistenceQuery, EventEnvelope }
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal

object Reporter extends App {
  val system = ActorSystem("persistent-query")
  implicit val mat = ActorMaterializer()(system)
  val queries = PersistentQuery(system)
  val evts: Source[EventEnvelope, Unit] = ???
  Thread.sleep(1000)
  system.terminate()
}


