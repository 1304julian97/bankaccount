package com.practice.bankaccount.application.cqrs

import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.service.AccountService

import scala.concurrent.Future

object CommanOpenAccount {

  def execute( number: Int, balance: Int, accountType: String )( context: Context ): Future[Either[String, BankAccount]] = {
    AccountService.openAccount( number, balance, accountType )( context.accountRepository )
  }

}
