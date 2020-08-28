package com.practice.bankaccount.domain.service

object AccountType extends Enumeration {

  type AccountType = Value

  val savingsAccount = Value("S")
  val checkingAccount = Value("C")

}
