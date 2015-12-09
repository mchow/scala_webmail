name := "scala_webmail"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.typesafe" % "config" % "1.3.0",
  ws
)

fork in run := true

mainClass in Compile := Some("webmail.WebMail")

