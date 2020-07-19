package com.practice.bankaccount.domain.model

object AccountStatus extends Enumeration {
  type AccountStatus = Value

  val BLOCKED = Value( "BLOCKED" )
  val FROZEN = Value( "FROZEN" )
  val ACTIVE = Value( "ACTIVE" )
}