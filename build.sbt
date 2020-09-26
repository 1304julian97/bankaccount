name := "bankaccount"
version := "0.1"
scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka"   %% "akka-http"        % "10.1.1",
  "org.typelevel"       %% "cats-core"        % "2.1.1",
  "com.typesafe"        %  "config"           % "1.4.0",
  "org.scalatest"       %% "scalatest"        % "3.1.2",
  "de.heikoseeberger"   %% "akka-http-circe"  % "1.20.1",
  "io.circe"            %% "circe-core"       % "0.9.3",
  "io.circe"            %% "circe-generic"    % "0.9.3",
  "io.circe"            %% "circe-parser"     % "0.9.3",
  "io.circe"            %% "circe-java8"      % "0.9.3"
)