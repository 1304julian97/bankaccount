name := "bankaccount"
version := "0.1"
scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "io.circe"            %% "circe-core"       % "0.9.3",
  "io.circe"            %% "circe-generic"    % "0.9.3",
  "io.circe"            %% "circe-java8"      % "0.9.3",
  "io.circe"            %% "circe-parser"     % "0.9.3",
  "com.h2database"      %   "h2"              % "1.4.189",
  "com.typesafe.akka"   %% "akka-http"        % "10.1.1",
  "com.typesafe"        %  "config"           % "1.4.0",
  "com.typesafe.slick"  %% "slick"            % "3.3.0",
  "de.heikoseeberger"   %% "akka-http-circe"  % "1.20.1",
  "org.scalatest"       %% "scalatest"        % "3.1.2",
  "org.typelevel"       %% "cats-core"        % "2.1.1"
)