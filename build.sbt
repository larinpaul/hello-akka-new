ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.7"

lazy val root = (project in file("."))
  .settings(
    name := "hello-akka-new"
  )


libraryDependencies ++= Seq(
  // added the "akka-actor" dependency
  "com.typesafe.akka" %% "akka-actor" % "2.4.0"
)

