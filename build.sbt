name := "bankaccount"

version := "0.1"

scalaVersion := "2.12.4"

val akkaVersion = "2.5.26"

val akkaHttpVersion = "10.1.11"

val akkaHttp = "10.1.1"
val akkaHttpCirce = "1.20.1"
val circe = "0.9.3"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.1",
  "org.typelevel"   %% "cats-core"  % "2.1.1",
  "com.typesafe"    %  "config"     % "1.4.0",
  "org.scalatest"   %% "scalatest"  % "3.1.2",
  "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirce,
  "io.circe" %% "circe-core" % circe,
  "io.circe" %% "circe-generic" % circe,
  "io.circe" %% "circe-parser" % circe,
  "io.circe" %% "circe-java8" % circe

)