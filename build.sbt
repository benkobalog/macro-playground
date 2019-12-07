name := "CirceDebug"
organization in ThisBuild := "io.circe"
version in ThisBuild := "0.1"
scalaVersion in ThisBuild := "2.13.1"

val circeVersion = "0.12.1"

libraryDependencies in ThisBuild ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion) ++ Seq(
  "org.scala-lang" % "scala-compiler" % "2.13.1"
)
//scalacOptions in ThisBuild += "-Ymacro-debug-lite"

lazy val global = project.in(file(".")).aggregate(macros, circeDebugTest)
lazy val macros = (project in file("macros"))
lazy val circeDebugTest =
  project.in(file("circe-debug-test"))
  .dependsOn(macros)