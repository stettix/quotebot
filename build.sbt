name := "quotebot"
organization := "net.janvsmachine"
version := "0.0.1"
scalaVersion := "2.12.3"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "com.danielasfregola" %% "twitter4s" % "5.3",
  "ch.qos.logback" % "logback-classic" % "1.1.9",
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)
