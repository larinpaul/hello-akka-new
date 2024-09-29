
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.7"

lazy val root = (project in file("."))
  .settings(
    name := "hello-akka-new"
  )


libraryDependencies ++= Seq(
  // added the "akka-actor" dependency
  "com.typesafe.akka" %% "akka-actor" % "2.4.20",
  "com.typesafe.akka" %% "akka-stream" % "2.4.20",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.20",
  "com.typesafe.akka" %% "akka-persistence-query" % "2.4.20",
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
)
