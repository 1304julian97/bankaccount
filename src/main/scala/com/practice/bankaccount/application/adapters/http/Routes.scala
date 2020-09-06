package com.practice.bankaccount.application.adapters.http

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.{ concat, get, parameter, pathPrefix }
import akka.http.scaladsl.server.Route
import com.practice.bankaccount.application.PersitenceContext
import com.practice.bankaccount.application.commandqueries.{ CommanUpsertAccounts, QueryGetAccounts }
import com.practice.bankaccount.application.dto.{ BankAccountDTO, RestResponse }
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

trait Routes extends JsonDecoders {

  def context: PersitenceContext

  val route: Route =
    path( "bank-accounts" ) {
      get {
        val response: RestResponse[List[BankAccountDTO]] = QueryGetAccounts.execute( context )
        complete( HttpEntity( ContentTypes.`application/json`, response.toString ) )
      } ~
        post {
          entity( as[BankAccountDTO] ) { ba =>
            val response: RestResponse[String] = CommanUpsertAccounts.execute( ba )( context )
            complete( HttpEntity( ContentTypes.`application/json`, response.toString ) )
          }
        }
    }

}

