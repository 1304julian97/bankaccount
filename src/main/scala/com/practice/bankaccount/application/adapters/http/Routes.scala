package com.practice.bankaccount.application.adapters.http

import java.time.ZonedDateTime

import akka.http.scaladsl.model.StatusCodes.{ InternalServerError, OK, RequestTimeout }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.{ concat, get, parameter, pathPrefix }
import akka.http.scaladsl.server.{ Route, StandardRoute }
import com.practice.bankaccount.application.cqrs.{ CommanOpenAccount, QueryGetAccounts }
import com.practice.bankaccount.application.dto.ApplicationDto._
import com.practice.bankaccount.application.dto._
import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ Await, Future }
import scala.util.{ Failure, Success, Try }

trait Routes extends MappersDto with JsonDecoders {

  private def now = ZonedDateTime.now()

  def context: Context

  val route: Route =
    path( "bank-accounts" ) {
      get {
        val result: Future[Either[String, List[BankAccount]]] = QueryGetAccounts.execute( context )
        val responseRaw: Future[StandardRoute] = result.map {
          case Right( list )       => complete( OK -> GetAccountsResponse( now.toString, list.map( mapBankAccountToDTO ) ) )
          case Left( erroMessage ) => complete( InternalServerError -> ErrorResponse( now.toString, erroMessage ) )
        }
        val finalResponse = Try( Await.result( responseRaw, 5.seconds ) )
        finalResponse match {
          case Success( value ) => value
          case _                => complete( RequestTimeout -> ErrorResponse( now.toString, "Something was wrong" ) )
        }
        /*result.value match {
          case Some( trySome ) => trySome match {
            case Success( value ) => value match {
              case Right( bankAccounts ) => complete( OK -> GetAccountsResponse( now.toString, bankAccounts.map( mapBankAccountToDTO ) ) )
              case Left( error )         => complete( InternalServerError -> ErrorResponse( now.toString, error ) )
            }
            case Failure( throwable ) => complete( InternalServerError -> ErrorResponse( now.toString, s"Something was wrong getting accounts: Message: '${throwable.getMessage}'" ) )
          }
          case _ => complete( InternalServerError -> ErrorResponse( now.toString, "Something was wrong getting accounts" ) )
        }*/
      } ~
        post {
          entity( as[OpenAccountRequest] ) { request =>
            val result = CommanOpenAccount.execute( request.number, request.balance, request.accountType )( context )
            result.value match {
              case Some( trySome ) => trySome match {
                case Success( value ) => value match {
                  case Right( bankAccount ) => complete( OK -> OpenAccountResponse( now.toString, mapBankAccountToDTO( bankAccount ) ) )
                  case Left( error )        => complete( InternalServerError -> ErrorResponse( now.toString, error ) )
                }
                case Failure( throwable ) => complete( InternalServerError -> ErrorResponse( now.toString, s"Something was wrong getting accounts: Message: '${throwable.getMessage}'" ) )
              }
              case _ => complete( InternalServerError -> ErrorResponse( now.toString, "Something was wrong getting accounts" ) )
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

