package com.practice.bankaccount.application.adapters.http

import java.time.ZonedDateTime

import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.{ concat, get, parameter, pathPrefix }
import akka.http.scaladsl.server.Route
import com.practice.bankaccount.application.PersitenceContext
import com.practice.bankaccount.application.commandqueries.{ CommanUpsertAccounts, QueryGetAccounts }
import com.practice.bankaccount.application.dto.{ BankAccountDTO, RestResponse, StatusDTO }
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

trait Routes extends JsonDecoders {

  protected def now: ZonedDateTime = ZonedDateTime.now

  def context: PersitenceContext

  val route: Route =
    path( "bank-accounts" ) {
      get {
        val response: RestResponse[List[BankAccountDTO]] = QueryGetAccounts.execute( context )
        val accounts: List[BankAccountDTO] = response.objectResponse
        complete( OK -> accounts )
      } ~
        post {
          entity( as[BankAccountDTO] ) { account =>
            val response: RestResponse[String] = CommanUpsertAccounts.execute( account )( context )
            val createAccountDTO = response.businessException
            complete( OK -> createAccountDTO )
          }
        }
    } ~
      path( "status" ) {
        get {
          complete( OK -> StatusDTO( now.toString, "UP!" ) )
        }
      }

}

