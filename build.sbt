////
////ThisBuild / version := "0.1.0-SNAPSHOT"
////
////ThisBuild / scalaVersion := "2.11.7"
////
////lazy val root = (project in file("."))
////  .settings(
////    name := "hello-akka-new"
////  )
////
////
////libraryDependencies ++= Seq(
////  // added the "akka-actor" dependency
////  "com.typesafe.akka" %% "akka-actor" % "2.4.20", // Yeah, maybe wrong version, and the previous version was better after all...
////  "com.typesafe.akka" %% "akka-stream" % "2.4.20",
////  "com.typesafe.akka" %% "akka-persistence" % "2.4.20",
////  "com.typesafe.akka" %% "akka-persistence-query" % "2.4.20",
////  "org.iq80.leveldb" % "leveldb" % "0.7",
////  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
////  "com.typesafe.akka" %% "akka-remote" % "2.3.12"
////)
//
//// Building a Cluster
//
//name := "Akka Cluster"
//
//version := "1.0"
//
//scalaVersion := "2.11.7"
//
//sbtVersion := "0.13.5"
//
//resolvers += "Akka Snapshot Repozitory" at "http://repo.akka.io/snapshots/"
//
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
//  "com.typesafe.akka" %% "akka-remote" % "2.4.0",
//  "com.typesafe.akka" %% "akka-cluster" % "2.4.0",
//  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.0",
//  "com.typesafe.akka" %% "akka-persistence" % "2.4.0",
//  "com.typesafe.akka" %% "akka-contrib" % "2.4.0",
//  "org.iq80.leveldb" % "leveldb" % "0.7",
//  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
//
//  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
//  "com.typesafe.akka" %% "akka-testkit" % "2.4.0" % "test",)


import com.typesafe.sbt.SbtMultiJvm
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

val akkaVersion = "2.4.0"

val project = Project(
  id = "akka-sample-multi-node-scala",
  base = file("."),
  settings = Project.defaultSettings ++ SbtMultiJvm.multiJvmSettings ++ Seq(
    name := "cluster-multi-node-testing",
    version := "2.3.11",
    scalaVersion := "2.11.7",
    sbtVersion := "0.13.5",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-remote" % akkaVersion,
      "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
      "org.scalatest" %% "scalatest" % "2.2.4"),
    // make sure that MultiJvm test are compiled by the default test compilation
    compile in MultiJvm <<= (compile in MultiJvm) triggeredBy (compile in Test),
    // disable parallel tests
    parallelExecution in Test := false,
    // make sure that MultiJvm tests are executed by the default test target,
    // and combine the results from ordinary test and multi-jvm tests
    executeTests in Test <<= (executeTets in Test, executeTetsts in MultiJvm) map {
      case (testResults, multiNodeResults) =>
        val overall =
          if (testResults.overall.id < multiNodeResults.overall.id)
            multiNodeResults.overall
          else
            testResults.overall
        Tests.Output(overall,
          testResults.events ++ multiNodeResults.events,
          testResults.summaries ++ multiNodeResults.summaries)
    }
  )
) configs (MultiJvm)

