package com.practice.bankaccount.infrastructure.persistence.repository

import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.repository.AccountRepository
import scala.collection.mutable

class AccountRepositoryInMemory extends AccountRepository {

  private var accounts: mutable.Map[Int, BankAccount] = scala.collection.mutable.Map[Int, BankAccount]()

  def upsert( account: BankAccount ): Either[String, BankAccount] = {
    accounts( account.number ) = account
    Right( account )
  }

  def list(): Either[String, List[BankAccount]] = {
    Right( accounts.values.toList )
  }

}
