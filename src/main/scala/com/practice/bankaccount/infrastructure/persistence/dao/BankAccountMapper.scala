package com.practice.bankaccount.infrastructure.persistence.dao

import com.practice.bankaccount.domain.model.AccountStatus.AccountStatus
import com.practice.bankaccount.domain.model.{ AccountStatus, CheckingAccount, SavingsAccount }

object BankAccountMapper {

  def convertBankAccountDAOToSavingAccoutEntity( dto: BankAccountDAO ): SavingsAccount = {
    val status = convertAccountStatusStringToEnum( dto.status )
    SavingsAccount( dto.accountNumber, dto.startDate, status, dto.balance )
  }

  def convertBankAccountDAOToCheckingAccoutEntity( dto: BankAccountDAO ): CheckingAccount = {
    val status = convertAccountStatusStringToEnum( dto.status )
    CheckingAccount( dto.accountNumber, dto.startDate, status, dto.balance )
  }

  def convertSavingAccountEntityToDAO( entity: SavingsAccount ): BankAccountDAO = {
    val status = convertAccountStatusEnumToString( entity.status )
    BankAccountDAO( entity.number, entity.openDate, status, entity.balance )
  }

  def convertCheckingAccountEntityToDAO( entity: CheckingAccount ): BankAccountDAO = {
    val status = convertAccountStatusEnumToString( entity.status )
    BankAccountDAO( entity.number, entity.openDate, status, entity.balance )
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
