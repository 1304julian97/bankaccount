package com.practice.bankaccount.application.adapters.http

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.model.StatusCodes.{ InternalServerError, OK }
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.{ concat, get, parameter, pathPrefix }
import akka.http.scaladsl.server.Route
import com.practice.bankaccount.application.cqrs.{ CommanOpenAccount, QueryGetAccounts }
import com.practice.bankaccount.application.dto.ApplicationDto._
import com.practice.bankaccount.application.dto._
import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

trait Routes extends MappersDto with JsonDecoders {

  private def now = ZonedDateTime.now()

  def context: Context

  val route: Route =
    path( "bank-accounts" ) {
      get {
        val result: Either[String, List[BankAccount]] = QueryGetAccounts.execute( context )
        result match {
          case Right( bankAccounts ) => complete( OK -> GetAccountsResponse( now.toString, bankAccounts.map( mapBankAccountToDTO ) ) )
          case Left( error )         => complete( InternalServerError -> ErrorResponse( now.toString, error ) )
        }
      } ~
        post {
          entity( as[OpenAccountRequest] ) { request =>
            val result = CommanOpenAccount.execute( request.number, request.balance, request.accountType )( context )
            result match {
              case Right( bankAccount ) => complete( OK -> OpenAccountResponse( now.toString, mapBankAccountToDTO( bankAccount ) ) )
              case Left( error )        => complete( InternalServerError -> ErrorResponse( now.toString, error ) )
            }
          }
        }
    } ~
      path( "status" ) {
        get {
          complete( OK -> GetStatusResponse( now.toString, "UP!" ) )
        }
      }

}

