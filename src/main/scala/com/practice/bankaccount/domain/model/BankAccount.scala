package com.practice.bankaccount.domain.model

import java.time.LocalDateTime

sealed trait BankAccount {
  val number: Int
  val openDate: LocalDateTime
  val status: Status
  val balance: Int
}

case class SavingsAccount( number: Int, openDate: LocalDateTime, status: Status, balance: Int, rate: Double ) extends BankAccount
case class CheckingAccount( number: Int, openDate: LocalDateTime, status: Status, balance: Int ) extends BankAccount

object BankAccount {

  private def validateNumber( number: Int ): Either[String, Int] = {
    if ( number <= 0 ) Left( s"Account number must be greater than 0. Received '$number'" )
    else Right( number )
  }

  private def validateBalance( balance: Int ): Either[String, Int] = {
    if ( balance < 0 ) Left( s"Balance must not be negative. Received '$balance'" )
    else Right( balance )
  }

  private def validateRate( rate: Double ): Either[String, Double] = {
    if ( rate < 0.1 && rate > 2.3 ) Left( s"Rate of interest must be between 0.1% and 2.3%. Received '$rate'" )
    else Right( rate )
  }

  def createSavingsAccount( number: Int, balance: Int, rate: Double ): Either[String, SavingsAccount] = {
    for {
      validNumber <- validateNumber( number )
      validBalance <- validateBalance( balance )
      validRate <- validateRate( rate )
    } yield SavingsAccount( validNumber, LocalDateTime.now, ACTIVE, validBalance, validRate )
  }

  def createCheckingAccount( number: Int, balance: Int ): Either[String, CheckingAccount] = {
    for {
      validNumber <- validateNumber( number )
      validBalance <- validateBalance( balance )
    } yield CheckingAccount( validNumber, LocalDateTime.now, ACTIVE, validBalance )
  }

}