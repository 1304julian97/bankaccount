package com.practice.bankaccount.domain.repository

import com.practice.bankaccount.infrastructure.persistence.dao.BankAccountDAO

trait AccountRepository {

  def upsert( account: BankAccountDAO ): Either[String, BankAccountDAO]

  def list(): Either[String, List[BankAccountDAO]]

}
