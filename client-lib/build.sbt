name := "client-lib"

organization := "uk.co.g4me.cas"

version := "1.0.0"

scalaVersion := "2.11.5"

resolvers += Resolver.url("Objectify Play Repository", url("http://deadbolt.ws/releases/"))(Resolver.ivyStylePatterns)

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.9"

libraryDependencies ++= Seq(
  "org.pac4j" % "play-pac4j_scala2.11" % "1.3.0",         // CAS server interface library
  "org.pac4j" % "pac4j-cas" % "1.6.0",                    // CAS protocol
  "be.objectify" %% "deadbolt-scala" % "2.3.1"			  // Deadbolt 2 Scala
)