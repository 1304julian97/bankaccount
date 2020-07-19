package com.practice.bankaccount.domain.service

import com.practice.bankaccount.domain.model.{ AccountStatus, BankAccount, CheckingAccount, SavingsAccount }
import com.practice.bankaccount.domain.repository.AccountRepository

object AccountService {

  def openAccount( number: Int, balance: Int, accountType: String )( repository: AccountRepository ): Either[String, BankAccount] = {
    if ( accountType != "S" && accountType != "C" ) {
      Left( "Invalid account type" )
    } else {
      /*val result: Either[String, BankAccount] = accountType match {
        case "S" => BankAccount.createSavingsAccount( number, balance )
        case "C" => BankAccount.createCheckingAccount( number, balance )
      }

      result match {
        case Left( error )    => Left( error )
        case Right( account ) => repository.upsert( account )
      }*/

      for {
        createdAccount <- {
          accountType match {
            case "S" => BankAccount.createSavingsAccount( number, balance )
            case "C" => BankAccount.createCheckingAccount( number, balance )
          }
        }
        resultUpsert <- repository.upsert( createdAccount )
      } yield resultUpsert

    }

  }

  def listAllAcounts()( repository: AccountRepository ): Either[String, List[BankAccount]] = {
    repository.list()
  }

}
