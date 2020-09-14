package com.practice.bankaccount.application

import com.practice.bankaccount.domain.repository.AccountRepository

trait Context {

  val accountRepository: AccountRepository

}
