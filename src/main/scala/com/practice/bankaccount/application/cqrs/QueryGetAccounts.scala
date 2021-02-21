package com.practice.bankaccount.application.cqrs

import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.service.AccountService
import monix.eval.Task


object QueryGetAccounts {

  def execute( context: Context ): Task[Either[String, List[BankAccount]]] = {
    AccountService.listAcounts()( context.accountRepository )
  }

}
