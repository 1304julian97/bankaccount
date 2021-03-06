package com.practice.bankaccount.domain.service

import cats.data.EitherT
import cats.implicits._
import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.repository.AccountRepository
import monix.eval.Task

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

object AccountService {

  private def checkAccountType( accountType: String ): Future[Either[String, String]] = Future {
    if ( accountType == "S" || accountType == "C" ) Right( accountType )
    else Left( s"Account type '$accountType' to open is not valid" )
  }

  def openAccount( number: Int, balance: Int, accountType: String )( repository: AccountRepository ): Future[Either[String, BankAccount]] = {

    for {
      validationResult <- checkAccountType( accountType )
      newAccount <- Future {
        validationResult match {
          case Right( "S" )         => BankAccount.createSavingsAccount( number, balance, 0.0 )
          case Right( "C" )         => BankAccount.createCheckingAccount( number, balance )
          case Left( errorMessage ) => Left( errorMessage )
        }
      }
      savedAccount <- {
        newAccount match {
          case Right( account )     => repository.upsert( account )
          case Left( errorMessage ) => Future( Left( errorMessage ) )
        }
      }
    } yield savedAccount

  }

  def listAcounts()( repository: AccountRepository ): Task[Either[String, List[BankAccount]]] = {
    repository.list()
  }

  def filterAccountByBalanceLimit( balanceLimit: Int )( repository: AccountRepository ): Task[Either[String, List[BankAccount]]] = {
    val f: List[BankAccount] => Task[Either[String, List[BankAccount]]] = ( accounts: List[BankAccount] ) => Task( Right( accounts.filter( _.balance <= balanceLimit ) ) )

    val accountsFiltered = for {
      accounts <- EitherT( listAcounts()( repository ) )
      filter <- EitherT( f.apply( accounts ) )
    } yield filter
    accountsFiltered.value

  }

}
