package com.practice.bankaccount.domain.repository

import com.practice.bankaccount.domain.model.BankAccount

trait AccountRepository {

  def upsert( bankAccount: BankAccount ): Either[String, BankAccount]

  def list(): Either[String, List[BankAccount]]

}
