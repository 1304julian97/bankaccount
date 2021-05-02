package com.practice.bankaccount.domain.repository

import com.practice.bankaccount.domain.model.BankAccount
import monix.eval.Task

import scala.concurrent.Future

trait AccountRepository {

  def upsert( bankAccount: BankAccount ): Future[Either[String, BankAccount]]

  def list(): Task[Either[String, List[BankAccount]]]

  def getAccount( accountNumber: Int ): Task[Either[String, BankAccount]]

}
