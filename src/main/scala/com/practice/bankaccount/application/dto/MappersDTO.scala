package com.practice.bankaccount.application.dto

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.practice.bankaccount.domain.model.AccountStatus.AccountStatus
import com.practice.bankaccount.domain.model.{ AccountStatus, BankAccount, CheckingAccount, SavingsAccount }

object MappersDTO {

  private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" )

  def convertBankAccountDTOToSavingAccoutEntity( dto: BankAccountDTO ): SavingsAccount = {
    val status = convertAccountStatusStringToEnum( dto.status )
    val dateTime: LocalDateTime = LocalDateTime.parse( dto.startDate, formatter )
    SavingsAccount( dto.accountNumber, dateTime, status, dto.balance )
  }

  def convertBankAccountDTOToCheckingAccoutEntity( dto: BankAccountDTO ): CheckingAccount = {
    val status = convertAccountStatusStringToEnum( dto.status )
    val dateTime: LocalDateTime = LocalDateTime.parse( dto.startDate, formatter )
    CheckingAccount( dto.accountNumber, dateTime, status, dto.balance )
  }

  def convertBankAccountEntityToDTO( account: BankAccount ): BankAccountDTO = {
    val status = convertAccountStatusEnumToString( account.status )
    val dateTime = formatter.format( account.openDate )
    BankAccountDTO( account.number, dateTime, status, account.balance )
  }

  def convertAccountStatusEnumToString( status: AccountStatus ): String = {
    status match {
      case AccountStatus.BLOCKED => "BLOCKED"
      case AccountStatus.FROZEN  => "FROZEN"
      case AccountStatus.ACTIVE  => "ACTIVE"
    }
  }

  def convertAccountStatusStringToEnum( status: String ): AccountStatus = {
    status match {
      case "BLOCKED" => AccountStatus.BLOCKED
      case "FROZEN"  => AccountStatus.FROZEN
      case "ACTIVE"  => AccountStatus.ACTIVE
    }
  }

}
