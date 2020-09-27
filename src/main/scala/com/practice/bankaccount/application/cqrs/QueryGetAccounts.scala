package com.practice.bankaccount.application.cqrs

import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.service.AccountService

object QueryGetAccounts {

  def execute( context: Context ): Either[String, List[BankAccount]] = {
    AccountService.listAcounts()( context.accountRepository )
  }

}
