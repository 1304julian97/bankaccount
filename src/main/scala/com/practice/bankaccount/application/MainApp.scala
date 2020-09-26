package com.practice.bankaccount.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.practice.bankaccount.application.adapters.http.{ JsonDecoders, Routes }
import com.practice.bankaccount.infrastructure.context.ContextInMemory
import com.typesafe.config.{ Config, ConfigFactory }

import scala.concurrent.{ Await, ExecutionContextExecutor }
import scala.util.{ Failure, Success }

object MainApp extends App with JsonDecoders with Routes {

  private val config: Config = ConfigFactory.load()
  private val name: String = config.getString( "app.name" )

  implicit val system: ActorSystem = ActorSystem( "HttpAdapter" )
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val context: Context = new ContextInMemory()

  println( s"Running application $name" )
  val server = Http().bindAndHandle( route, "localhost", 8080 )

  server.onComplete {
    case Success( Http.ServerBinding( localAddress ) ) =>
      println( s"Server online at ${localAddress.getAddress}:${localAddress.getPort}" )
    case Failure( ex ) =>
      println( s"There was an error while starting server", ex )
  }

  // PostStop
  sys.addShutdownHook {
    println( "PostStop" )
  }

}