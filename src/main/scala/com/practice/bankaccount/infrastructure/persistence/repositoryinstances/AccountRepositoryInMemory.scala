package com.practice.bankaccount.infrastructure.persistence.repositoryinstances

import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.dao.BankAccountDAO

import scala.collection.mutable

class AccountRepositoryInMemory extends AccountRepository {

  private var accounts: mutable.Map[Int, BankAccountDAO] = scala.collection.mutable.Map[Int, BankAccountDAO]()

  def upsert( account: BankAccountDAO ): Either[String, BankAccountDAO] = {
    accounts( account.accountNumber ) = account
    Right( account )
  }

  def list(): Either[String, List[BankAccountDAO]] = {
    Right( accounts.values.toList )
  }

}
