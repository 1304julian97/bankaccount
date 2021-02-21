package com.practice.bankaccount.application.cqrs

import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.service.AccountService
import monix.eval.Task

import scala.concurrent.Future

object QueryFilterAccount {

  def execute( balanceLimit: Int )( context: Context ): Task[Either[String, List[BankAccount]]] = {
    AccountService.filterAccountByBalanceLimit( balanceLimit )( context.accountRepository )
  }

}
