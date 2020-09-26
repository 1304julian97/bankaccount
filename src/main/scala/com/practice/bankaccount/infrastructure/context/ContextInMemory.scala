package com.practice.bankaccount.infrastructure.context

import com.practice.bankaccount.application.Context
import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.AccountRepositoryInMemory

class ContextInMemory extends Context {

  val accountRepository: AccountRepository = new AccountRepositoryInMemory()

}
