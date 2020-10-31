package com.practice.bankaccount.application.adapters.http

import java.time.ZonedDateTime
import java.util.UUID

import akka.http.scaladsl.model.StatusCodes.{ InternalServerError, OK, RequestTimeout }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.get
import akka.http.scaladsl.server.Route
import com.practice.bankaccount.application.cqrs.{ CommanOpenAccount, QueryFilterAccount, QueryGetAccounts }
import com.practice.bankaccount.application.dto.ApplicationDto._
import com.practice.bankaccount.application.dto._
import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.util.{ Failure, Success }

trait Routes extends MappersDto with JsonDecoders {

  private def now = ZonedDateTime.now()

  def context: Context

  val route: Route =
    path( "bank-accounts" ) {
      get {
        val result: Future[Either[String, List[BankAccount]]] = QueryGetAccounts.execute( context )

        onComplete( result ) {
          case Success( Right( list ) )        => complete( OK -> GetAccountsResponse( now.toString, list.map( mapBankAccountToDTO ) ) )
          case Success( Left( errorMessage ) ) => complete( InternalServerError -> ErrorResponse( now.toString, errorMessage ) )
          case Failure( ex ) =>
            val messageId: String = UUID.randomUUID().toString
            LoggerFactory.getLogger( "Routes.class" ).error( s"Something was wrong, Use this code: $messageId", ex )
            complete( RequestTimeout -> ErrorResponse( now.toString, "Something was wrong" ) )
        }

      } ~ get {
        //bank-accounts/qtyLimit={someNumber}
        headerValueByName( "token" ) { token =>
          parameter( "qtyLimit" ) { qtyLimit =>
            val result: Future[Either[String, List[BankAccount]]] = QueryFilterAccount.execute( qtyLimit.toInt )( context )
            onComplete( result ) {
              case Success( Right( list ) )        => complete( OK -> GetAccountsResponse( now.toString, list.map( mapBankAccountToDTO ) ) )
              case Success( Left( errorMessage ) ) => complete( InternalServerError -> ErrorResponse( now.toString, errorMessage ) )
              case Failure( ex ) =>
                val messageId: String = UUID.randomUUID().toString
                LoggerFactory.getLogger( "Routes.class" ).error( s"Something was wrong getting accounts with balance greater than $qtyLimit, Use this code: $messageId", ex )
                complete( RequestTimeout -> ErrorResponse( now.toString, "Something was wrong" ) )

            }
          }
        }

      } ~
        post {
          entity( as[OpenAccountRequest] ) { request =>
            val result = CommanOpenAccount.execute( request.number, request.balance, request.accountType )( context )

            onComplete( result ) {
              case Success( Right( bankAccount ) ) => complete( OK -> OpenAccountResponse( now.toString, mapBankAccountToDTO( bankAccount ) ) )
              case Success( Left( errorMessage ) ) => complete( InternalServerError -> ErrorResponse( now.toString, errorMessage ) )
              case Failure( ex ) =>
                val messageId: String = UUID.randomUUID().toString
                LoggerFactory.getLogger( "Routes.class" ).error( s"Something was wrong, Use this code: $messageId", ex )
                complete( RequestTimeout -> ErrorResponse( now.toString, "Something was wrong" ) )
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

