package com.practice.bankaccount.domain.model

import java.time.LocalDateTime

import com.practice.bankaccount.domain.model.AccountStatus.AccountStatus

sealed trait BankAccount {
  val number: Int
  val openDate: LocalDateTime
  val status: AccountStatus
  val balance: Int
}

case class SavingsAccount( number: Int, openDate: LocalDateTime, status: AccountStatus, balance: Int ) extends BankAccount

case class CheckingAccount( number: Int, openDate: LocalDateTime, status: AccountStatus, balance: Int ) extends BankAccount

object BankAccount {
  def createSavingsAccount( number: Int, balance: Int ): Either[String, SavingsAccount] = {
    if ( number <= 0 ) {
      Left( "Account number must be greater than 0" )
    } else if ( balance < 0 ) {
      Left( "Balance must not be negative" )
    } else {
      Right( SavingsAccount( number, LocalDateTime.now, AccountStatus.ACTIVE, balance ) )
    }
  }
  def createCheckingAccount( number: Int, balance: Int ): Either[String, CheckingAccount] = {
    if ( number <= 0 ) {
      Left( "Account number must be greater than 0" )
    } else if ( balance < 0 ) {
      Left( "Balance must not be negative" )
    } else {
      Right( CheckingAccount( number, LocalDateTime.now, AccountStatus.ACTIVE, balance ) )
    }
  }

}