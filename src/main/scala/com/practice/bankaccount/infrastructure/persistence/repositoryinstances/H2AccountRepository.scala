package com.practice.bankaccount.infrastructure.persistence.repositoryinstances

import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.dao.BankAccountDAO;

class H2AccountRepository extends AccountRepository {
  val port: Int = 8080
  val host: String = "localhost"
  val user: String = "DummyUser"
  val password: String = "DummyPassword"

  def upsert( account: BankAccountDAO ): Right[String, BankAccountDAO] = {
    Right( account )
  }

  def list(): Either[String, List[BankAccountDAO]] = {
    Left( "Something was wrong" )
  }
}
