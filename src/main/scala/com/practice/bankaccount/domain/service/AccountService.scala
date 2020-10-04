package com.practice.bankaccount.domain.service

import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.repository.AccountRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AccountService {

  private def checkAccountType( accountType: String ): Either[String, String] = {
    if ( accountType == "S" || accountType == "C" ) Right( accountType )
    else Left( s"Account type '$accountType' to open is not valid" )
  }

  def openAccount( number: Int, balance: Int, accountType: String )( repository: AccountRepository ): Future[Either[String, BankAccount]] = {

    val defaultRate: Double = 0.0
    val validatedType = Future( checkAccountType( accountType ) )

    for {
      validType <- validatedType
      newAccount <- {
        if ( validType == "S" ) Future( BankAccount.createSavingsAccount( number, balance, defaultRate ) )
        else Future( BankAccount.createCheckingAccount( number, balance ) )
      }
      savedAccount <- {
        repository.upsert( newAccount match {
          case Right( bank ) => bank
          case Left( msg )   => throw new Exception( msg )
        } )
      }
    } yield savedAccount

  }

  def listAcounts()( repository: AccountRepository ): Future[Either[String, List[BankAccount]]] = {
    repository.list()
  }

}
