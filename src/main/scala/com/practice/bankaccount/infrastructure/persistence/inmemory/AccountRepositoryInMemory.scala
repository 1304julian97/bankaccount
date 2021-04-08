package com.practice.bankaccount.infrastructure.persistence.inmemory

import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.repository.AccountRepository
import monix.eval.Task

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.concurrent.Future

class AccountRepositoryInMemory extends AccountRepository with AccountDAOMapperInMemory {

  private var records: mutable.Map[Int, AccountDAORecordInMemory] = scala.collection.mutable.Map[Int, AccountDAORecordInMemory]()

  def upsert( bankAccount: BankAccount ): Future[Either[String, BankAccount]] = {
    val result: Either[String, AccountDAORecordInMemory] = fromBankAccountToDAORecord( bankAccount )

    Future {
      if ( result.isRight ) {
        records += ( result.right.get.number -> result.right.get )
        Right( bankAccount )
      } else {
        Left( result.left.get )
      }
    }
  }

  def list(): Task[Either[String, List[BankAccount]]] = {
    val resultSet: List[Either[String, BankAccount]] = records.values.toList
      .map( record => fromDAORecordToBankAccount( record ) )

    val accounts = resultSet
      .filter( result => result.isRight )
      .map( result => result.right.get )

    Task( Right( accounts ) )
  }

}
