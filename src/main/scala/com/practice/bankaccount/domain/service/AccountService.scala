package com.practice.bankaccount.domain.service

import com.practice.bankaccount.domain.model.{ Status, BankAccount, CheckingAccount, SavingsAccount }
import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.dao.BankAccountDAOMapper

object AccountService {

  private def checkAccountType( accountType: String ): Either[String, String] = {
    if ( accountType != "S" && accountType != "C" ) Left( s"Account type '$accountType' to open is not valid" )
    else Right( accountType )
  }

  def openAccount( number: Int, balance: Int, accountType: String )( repository: AccountRepository ): Either[String, BankAccount] = {

    val defaultRate: Double = 0.0

    for {
      validType <- checkAccountType( accountType )
      newAccount <- {
        if ( validType == "S" ) BankAccount.createSavingsAccount( number, balance, defaultRate )
        else BankAccount.createCheckingAccount( number, balance )
      }
      savedAccount <- repository.upsert( newAccount )
    } yield savedAccount

  }

  def listAcounts()( repository: AccountRepository ): Either[String, List[BankAccount]] = {
    repository.list()
  }

}
