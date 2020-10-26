package com.practice.bankaccount.domain.repository

import com.practice.bankaccount.domain.model.BankAccount

import scala.concurrent.Future

trait AccountRepository {

  def upsert( bankAccount: BankAccount ): Future[Either[String, BankAccount]]

  def list(): Future[Either[String, List[BankAccount]]]

}
