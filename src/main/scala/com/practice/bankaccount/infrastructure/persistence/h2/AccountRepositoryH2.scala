package com.practice.bankaccount.infrastructure.persistence.h2

import com.practice.bankaccount.domain.model.BankAccount
import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.h2.table.{ AccountDAOMapperH2, AccountDAORecordH2, H2Tables }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
import slick.jdbc.H2Profile.api._

class AccountRepositoryH2 extends AccountRepository with AccountDAOMapperH2 {

  val db = Database.forConfig( "h2.default" )
  private val action = H2Tables.bankAccounts.schema.create
  Await.ready( db.run( action ), 5.seconds )

  def upsert( bankAccount: BankAccount ): Future[Either[String, BankAccount]] = {
    val result: Either[String, AccountDAORecordH2] = fromBankAccountToDAORecord( bankAccount )

    Future {
      if ( result.isRight ) {
        val record: AccountDAORecordH2 = result.right.get

        val dbAction = H2Tables.bankAccounts.+=( record )

        val futureResult: Future[BankAccount] = db.run( dbAction ).map { result => bankAccount }
        Await.ready( futureResult, 5.seconds )

        Right( bankAccount )
      } else {
        Left( result.left.get )
      }
    }
  }

  def list(): Future[Either[String, List[BankAccount]]] = {
    val dbAction = H2Tables.bankAccounts.result

    val futureAccounts: Future[List[BankAccount]] = db.run( dbAction ).map { dbRecords =>
      dbRecords.toList.map( fromDAORecordToBankAccount )
        .filter( result => result.isRight )
        .map( result => result.right.get )
    }

    //val accounts: List[BankAccount] = Await.result( futureAccounts, 5.seconds )

    //Right( accounts )
    futureAccounts.map( list => Right( list ) )
  }

}
