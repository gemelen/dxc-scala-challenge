import sbt.Keys._
import D._

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / organization := "net.gemelen.dev.dxc"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dxc",
    libraryDependencies :=
      conf
      ++ cats
      ++ kantan
      ++ logging
      ++ tests
  )
