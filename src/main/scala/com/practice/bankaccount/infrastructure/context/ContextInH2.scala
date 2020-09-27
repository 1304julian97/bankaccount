package com.practice.bankaccount.infrastructure.context

import com.practice.bankaccount.application.main.Context
import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.h2.AccountRepositoryH2
import com.practice.bankaccount.infrastructure.persistence.inmemory.AccountRepositoryInMemory

class ContextInH2 extends Context {

  val accountRepository: AccountRepository = new AccountRepositoryH2()

}
