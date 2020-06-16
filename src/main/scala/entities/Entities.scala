package entities

import java.time.LocalDateTime

import AccoutState.AccountState


trait BanckAccount {
  val accountNumber: Int
  val startDate: LocalDateTime
  val state: AccountState
  val balance: Int
}


case class SavingsAccount(accountNumber: Int, startDate: LocalDateTime, state: AccountState, balance: Int) extends BanckAccount

case class CheckingAccount(accountNumber: Int, startDate: LocalDateTime, state: AccountState, balance: Int) extends BanckAccount


object AccoutState extends Enumeration {
  type AccountState = Value
  val BLOCKED: AccoutState.Value = Value("BLOCKED")
  val FROZEN: AccoutState.Value = Value("FROZEN")
  val ACTIVATED: AccoutState.Value = Value("ACTIVATED")

}

