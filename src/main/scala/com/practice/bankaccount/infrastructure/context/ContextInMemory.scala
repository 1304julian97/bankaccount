package com.practice.bankaccount.infrastructure.context

import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.inmemory.AccountRepositoryInMemory

class ContextInMemory extends Context {

  val accountRepository: AccountRepository = new AccountRepositoryInMemory()

}
