package com.practice.bankaccount.application.service

import com.practice.bankaccount.domain.model.{ BankAccount, SavingsAccount }
import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.dao.{ BankAccountDAORecord, BankAccountDAOMapper }

class AccountService {

  def saveUpdateAccount( bankAccount: BankAccount )( repository: AccountRepository ): Either[Throwable, String] = {
    val savingsAccountDAO = bankAccount match {
      case ba: SavingsAccount => BankAccountMapper.convertSavingAccountEntityToDAO( ba )
      case _                  => throw new Error( "paila" )

    }

    repository.upsert( savingsAccountDAO ) match {
      case Right( account ) => Right( s"Account number ${account.accountNumber} saved successfully" )
      case Left( error )    => Left( new Throwable( error ) )

    }

  }

  def getListAccount( repository: AccountRepository ): Either[Throwable, List[BankAccount]] = {
    val acountsTry: Either[String, List[BankAccountDAORecord]] = repository.list()

    acountsTry match {
      case Right( accounts ) => Right( accounts.map( ba => BankAccountMapper.convertBankAccountDAOToCheckingAccoutEntity( ba ) ) )
      case Left( error )     => Left( new Throwable( error ) )
    }
  }

}