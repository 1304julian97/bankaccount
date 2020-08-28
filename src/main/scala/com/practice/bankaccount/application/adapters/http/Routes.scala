package com.practice.bankaccount.application.adapters.http

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.practice.bankaccount.application.adapters.HttpJsonParser

trait Commands extends HttpJsonParser {

  val route =
    path( "bank-accounts" ) {
      get {
        complete( HttpEntity( ContentTypes.`application/json`, "Hola" ) )
      }
    }

}

