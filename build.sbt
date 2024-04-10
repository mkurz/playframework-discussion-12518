name := "gh_12518"
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.13"

libraryDependencies += guice
libraryDependencies += "org.assertj" % "assertj-core" % "3.24.2" % Test