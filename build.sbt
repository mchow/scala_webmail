name := "Webmail"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.typesafe" % "config" % "1.3.0",
  ws
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

fork in run := true


