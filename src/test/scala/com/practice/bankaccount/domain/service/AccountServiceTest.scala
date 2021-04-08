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
