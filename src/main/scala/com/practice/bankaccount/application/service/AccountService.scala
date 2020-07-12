package com.practice.bankaccount.application.service

import com.practice.bankaccount.domain.model.{ BankAccount, SavingsAccount }
import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.dao.BankAccountMapper

class AccountService {

  def saveUpdateAccount( bankAccount: BankAccount )( repository: AccountRepository ): Either[Throwable, String] = {
    val savingsAccountDAO = bankAccount match {
      case ba: SavingsAccount => BankAccountMapper.convertSavingAccountEntityToDAO( ba )
      case _                  => throw new Error( "paila" )

    }

    repository.upsert( bankAccount ) match {
      case Right( account ) => Right( s"Account number ${account.number} saved successfully" )
      case Left( error )    => Left( new Throwable( error ) )

    }

  }

  def getListAccount( repository: AccountRepository ): Either[Throwable, List[BankAccount]] = {
    val acountsTry: Either[String, List[BankAccount]] = repository.list()

    acountsTry match {
      case Right( accounts ) => Right( accounts )
      case Left( error )     => Left( new Throwable( error ) )
    }
  }

}