name := """auth-test"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.pac4j" % "play-pac4j_scala2.11" % "1.3.0",         // CAS server interface library
  "org.pac4j" % "pac4j-cas" % "1.6.0",                    // CAS protocol
  "com.adrianhurt" %% "play-bootstrap3" % "0.4",          // Play Twitter Bootstrap integration
  "org.webjars" % "font-awesome" % "4.2.0",               // Font awesome
  cache
)
