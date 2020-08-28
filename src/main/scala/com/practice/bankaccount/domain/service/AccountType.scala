package com.practice.bankaccount.domain.model

object AccountType extends Enumeration {

  type AccountType = Value

  val savingsAccount = Value("S")
  val checkingAccount = Value("C")

}
