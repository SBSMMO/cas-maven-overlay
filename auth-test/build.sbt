name := """auth-test"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "uk.co.g4me.cas" % "client-lib_2.11" % "1.0.0",		  // CAS Client Library
  "com.adrianhurt" %% "play-bootstrap3" % "0.4",          // Play Twitter Bootstrap integration
  "org.webjars" % "font-awesome" % "4.2.0",               // Font awesome
  cache
)
