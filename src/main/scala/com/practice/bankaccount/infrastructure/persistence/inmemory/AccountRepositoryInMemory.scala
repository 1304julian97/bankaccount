package com.practice.bankaccount.infrastructure.persistence.inmemory

import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.repository.AccountRepository
import scala.collection.mutable

class AccountRepositoryInMemory extends AccountRepository with BankAccountDAOMapperInMemory {

  private var records: mutable.Map[Int, BankAccountDAORecordInMemory] = scala.collection.mutable.Map[Int, BankAccountDAORecordInMemory]()

  def upsert( bankAccount: BankAccount ): Either[String, BankAccount] = {
    val result: Either[String, BankAccountDAORecordInMemory] = mapBankAccountToDAORecord( bankAccount )

    if ( result.isRight ) {
      records += ( result.right.get.number -> result.right.get )
      Right( bankAccount )
    } else {
      Left( result.left.get )
    }
  }

  def list(): Either[String, List[BankAccount]] = {
    val resultSet: List[Either[String, BankAccount]] = records.values.toList
      .map( record => mapDAORecordToBankAccount( record ) )

    val accounts = resultSet
      .filter( result => result.isRight )
      .map( result => result.right.get )

    Right( accounts )
  }

}
