package com.practice.bankaccount.application.dto

import com.practice.bankaccount.domain.model.AccountStatus.AccountStatus
import com.practice.bankaccount.domain.model.{ AccountStatus, BankAccount, CheckingAccount, SavingsAccount }

object MappersDTO {

  def convertBankAccountDTOToSavingAccoutEntity( dto: BankAccountDTO ): SavingsAccount = {
    val status = convertAccountStatusStringToEnum( dto.status )
    SavingsAccount( dto.accountNumber, dto.startDate, status, dto.balance )
  }

  def convertBankAccountDTOToCheckingAccoutEntity( dto: BankAccountDTO ): CheckingAccount = {
    val status = convertAccountStatusStringToEnum( dto.status )
    CheckingAccount( dto.accountNumber, dto.startDate, status, dto.balance )
  }

  def convertBankAccountEntityToDTO( entity: BankAccount ): BankAccountDTO = {
    val status = convertAccountStatusEnumToString( entity.status )
    BankAccountDTO( entity.number, entity.openDate, status, entity.balance )
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
