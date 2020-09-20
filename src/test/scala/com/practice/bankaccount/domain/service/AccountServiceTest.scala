package com.practice.bankaccount.domain.service

import com.practice.bankaccount.domain.model.{ ACTIVE, BankAccount, Status }
import com.practice.bankaccount.infrastructure.persistence.inmemory.AccountRepositoryInMemory
import org.scalatest.flatspec.AnyFlatSpec

class AccountServiceTest extends AnyFlatSpec {

  "AccountService" should "save a new account" in {

    val repository = new AccountRepositoryInMemory()
    val result: Either[String, BankAccount] = AccountService.openAccount( 8001, 50000, "S" )( repository )

    assert( result.isRight )
    assert( result.right.get.number == 8001 )
    assert( result.right.get.balance == 50000 )
    assert( result.right.get.status == ACTIVE )

  }

  it should "list saved accounts" in {

    val repository = new AccountRepositoryInMemory()
    val account1 = AccountService.openAccount( 8001, 50000, "S" )( repository )
    val account2 = AccountService.openAccount( 8002, 37000, "C" )( repository )

    val result: Either[String, List[BankAccount]] = repository.list()

    assert( result.isRight )
    assert( result.right.get.size == 2 )
    assert( result.right.get.exists( account => account.number == 8001 ) )
    assert( result.right.get.exists( account => account.number == 8002 ) )

  }

}
