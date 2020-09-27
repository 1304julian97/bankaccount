package com.practice.bankaccount.application.main

import com.practice.bankaccount.domain.repository.AccountRepository

trait Context {

  val accountRepository: AccountRepository

}
