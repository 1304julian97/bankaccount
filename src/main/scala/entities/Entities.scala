package entities

import java.time.LocalDateTime

import entities.AccountState.AccountState

trait BanckAccount {
  val accountNumber: Int
  val startDate: LocalDateTime
  val state: AccountState
  val balance: Int
}

case class SavingsAccount( accountNumber: Int, startDate: LocalDateTime, state: AccountState, balance: Int ) extends BanckAccount

case class CheckingAccount( accountNumber: Int, startDate: LocalDateTime, state: AccountState, balance: Int ) extends BanckAccount

object AccountState extends Enumeration {
  type AccountState = Value
  val BLOCKED = Value( "BLOCKED" )
  val FROZEN = Value( "FROZEN" )
  val ACTIVATED = Value( "ACTIVATED" )

}

