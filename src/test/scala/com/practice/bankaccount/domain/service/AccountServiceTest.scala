package com.practice.bankaccount.domain.service

import com.practice.bankaccount.domain.model.{ ACTIVE, BankAccount, Status }
import com.practice.bankaccount.infrastructure.persistence.h2.AccountRepositoryH2
import com.practice.bankaccount.infrastructure.persistence.inmemory.AccountRepositoryInMemory
import monix.eval.Task
import org.scalatest.flatspec.AnyFlatSpec
import monix.execution.Scheduler.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }

class AccountServiceTest extends AnyFlatSpec {

  "AccountService" should "save a new account" in {

    val repository = new AccountRepositoryInMemory()
    val rawResult: Future[Either[String, BankAccount]] = AccountService.openAccount( 8001, 50000, "S" )( repository )
    Await.ready( rawResult, 5.seconds )

    val result = rawResult.value.get.get

    assert( result.isRight )
    assert( result.right.get.number == 8001 )
    assert( result.right.get.balance == 50000 )
    assert( result.right.get.status == ACTIVE )

  }

  "Account Service" should "save in cache the account just created" in {
    val repository = new AccountRepositoryInMemory()
    val rawResult: Future[Either[String, BankAccount]] = AccountService.openAccount( 8003, 50000, "S" )( repository )

    val expected = Await.result( rawResult, 5.seconds )
    val accountCachabled = AccountService.getBanckAccountCache( expected.right.get.number )

    assertResult( expected.right.get, "The account cachable-expected was not found with the right values" )( accountCachabled.getOrElse( fail( s"The account $expected was not cacheabled" ) ) )
  }

  "Account service" should "Find a personal account created" in {
    val repository = new AccountRepositoryInMemory()
    val rawResult: Future[Either[String, BankAccount]] = AccountService.openAccount( 8004, 50000, "S" )( repository )

    val expected: Either[String, BankAccount] = Await.result( rawResult, 5.seconds )
    val valueRawFound = AccountService.getPersonalAccount( expected.right.get.number )( repository )

    val valueFound: Either[String, BankAccount] = Await.result( valueRawFound.runToFuture, 5.seconds )

    expected match {
      case Right( valueExpected ) => assertResult( valueExpected, s"The account created was not found" )( valueFound.right.get )
      case Left( value )          => fail( s"One error was found opening the account => $value" )
    }
  }

  "Account service" should "Find a personal account no created" in {
    val repository = new AccountRepositoryInMemory()
    val rawResult: Future[Either[String, BankAccount]] = AccountService.openAccount( 8005, 50000, "S" )( repository )

    val dummyAccountNumberToFind = 5008
    val accountCreated: Either[String, BankAccount] = Await.result( rawResult, 5.seconds )
    assert(accountCreated.isRight,"One error returned creating account")
    val valueRawFound = AccountService.getPersonalAccount( dummyAccountNumberToFind )( repository )

    val valueFound: Either[String, BankAccount] = Await.result( valueRawFound.runToFuture, 5.seconds )

    valueFound match {
      case Right( value )  => fail( s"The test should fail but this one found an account $value instead." )
      case Left( message ) => assertResult( s"The account with the number: $dummyAccountNumberToFind does not exist.", "The error message expected was incorrect" )( message )
    }
  }

  it should "list saved accounts" in {

    val repository = new AccountRepositoryH2()
    val account1 = AccountService.openAccount( 8001, 50000, "S" )( repository )
    val account2 = AccountService.openAccount( 8002, 37000, "C" )( repository )

    val resultRawTask: Task[Either[String, List[BankAccount]]] = repository.list()
    val resultRaw = resultRawTask.runToFuture
    val result = Await.result( resultRaw, 5.seconds )

    assert( result.isRight )
    assert( result.right.get.size == 2 )
    assert( result.right.get.exists( account => account.number == 8001 ) )
    assert( result.right.get.exists( account => account.number == 8002 ) )

  }

}
