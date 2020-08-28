package com.practice.bankaccount.application

import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.repositoryinstances.H2AccountRepository

class PersitenceContext {

  val repositoryH2: AccountRepository = new H2AccountRepository

}
