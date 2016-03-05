name := """gateway"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  "com.amazonaws" % "aws-java-sdk" % "1.10.58"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
