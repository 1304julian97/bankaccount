package com.practice.bankaccount.application.cqrs

import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.service.AccountService
import monix.eval.Task

object QueryGetPersonalAccount {

  def execute( accountNumber: Int )( context: Context ): Task[Either[String, BankAccount]] = {
    AccountService.getPersonalAccount( accountNumber )( context.accountRepository )
  }
}
