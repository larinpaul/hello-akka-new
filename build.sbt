//
//ThisBuild / version := "0.1.0-SNAPSHOT"
//
//ThisBuild / scalaVersion := "2.11.7"
//
//lazy val root = (project in file("."))
//  .settings(
//    name := "hello-akka-new"
//  )
//
//
//libraryDependencies ++= Seq(
//  // added the "akka-actor" dependency
//  "com.typesafe.akka" %% "akka-actor" % "2.4.20", // Yeah, maybe wrong version, and the previous version was better after all...
//  "com.typesafe.akka" %% "akka-stream" % "2.4.20",
//  "com.typesafe.akka" %% "akka-persistence" % "2.4.20",
//  "com.typesafe.akka" %% "akka-persistence-query" % "2.4.20",
//  "org.iq80.leveldb" % "leveldb" % "0.7",
//  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
//  "com.typesafe.akka" %% "akka-remote" % "2.3.12"
//)

// Building a Cluster

name := "Akka Cluster"

version := "1.0"

scalaVersion := "2.11.7"

sbtVersion := "0.13.5"

resolvers += "Akka Snapshot Repozitory" at "http://repo.akka.io/snapshots/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" %% "akka-remote" % "2.4.0",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.0",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.0",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.0",
  "com.typesafe.akka" %% "akka-contrib" % "2.4.0",
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",

  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.0" % "test")
