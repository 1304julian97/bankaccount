package com.practice.bankaccount.application.adapters.http

import java.time.ZonedDateTime
import java.util.UUID

import akka.http.scaladsl.model.StatusCodes.{ InternalServerError, OK, RequestTimeout }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.get
import akka.http.scaladsl.server.Route
import com.practice.bankaccount.application.cqrs.{ CommanOpenAccount, QueryGetAccounts }
import com.practice.bankaccount.application.dto.ApplicationDto._
import com.practice.bankaccount.application.dto._
import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import monix.eval.Task
import org.slf4j.LoggerFactory
import monix.execution.Scheduler.Implicits.global
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime

import scala.util.{Failure, Success}

trait Routes extends MappersDto with JsonDecoders {

  private def now = ZonedDateTime.now()

  def context: Context

  val route: Route =
    path( "bank-accounts" ) {
      get {
        val result: Task[Either[String, List[BankAccount]]] = QueryGetAccounts.execute( context ).timeout( 10.seconds ).
          doOnCancel( Task {
            val messageId: String = UUID.randomUUID().toString
            LoggerFactory.getLogger( "Routes.class" ).error( s"Time Out getting banks accounts, Use this code: $messageId" )
            complete( RequestTimeout -> ErrorResponse( now.toString, "Something was wrong" ) )
          } )

        onComplete( result.runToFuture ) {
          case Success( Right( list ) )        => complete( OK -> GetAccountsResponse( now.toString, list.map( mapBankAccountToDTO ) ) )
          case Success( Left( errorMessage ) ) => complete( InternalServerError -> ErrorResponse( now.toString, errorMessage ) )
          case Failure( ex ) =>
            val messageId: String = UUID.randomUUID().toString
            LoggerFactory.getLogger( "Routes.class" ).error( s"Something was wrong, Use this code: $messageId", ex )
            complete( RequestTimeout -> ErrorResponse( now.toString, "Something was wrong" ) )
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

