package com.practice.bankaccount.infrastructure.persistence.h2.table

import com.practice.bankaccount.domain.model.{ BankAccount, CheckingAccount, SavingsAccount, Status }
import com.practice.bankaccount.infrastructure.mapper.StatusMapper
import java.sql.Timestamp

trait AccountDAOMapperH2 {

  private def validateStatus( recordStatus: String ): Either[String, Status] = {
    StatusMapper.fromString( recordStatus ) match {
      case Some( status ) => Right( status )
      case None           => Left( s"Record status '$recordStatus' is not valid" )
    }
  }

  private def validateType( accountType: String ): Either[String, String] = {
    if ( accountType == "Savings" || accountType == "Checking" ) Right( accountType )
    else Left( s"Account type '$accountType' is not valid" )
  }

  def fromBankAccountToDAORecord( bankAccount: BankAccount ): Either[String, AccountDAORecordH2] = {

    val accountType: String = bankAccount match {
      case acc: SavingsAccount  => "Savings"
      case acc: CheckingAccount => "Checking"
    }
    val rate: Double = bankAccount match {
      case acc: SavingsAccount => acc.rate
      case _                   => 0.0
    }
    val status: String = StatusMapper.toString( bankAccount.status )
    val dateOfOpen = Timestamp.valueOf( bankAccount.openDate )

    Right( AccountDAORecordH2( bankAccount.number, accountType, dateOfOpen, status, bankAccount.balance, rate ) )
  }

  def fromDAORecordToBankAccount( record: AccountDAORecordH2 ): Either[String, BankAccount] = {

    val openDate = record.dateOfOpen.toLocalDateTime

    for {
      status <- validateStatus( record.accountStatus )
      accType <- validateType( record.accountType )
    } yield {
      if ( accType == "Savings" ) SavingsAccount( record.accountNo, openDate, status, record.currentBalance, record.accountRate )
      else CheckingAccount( record.accountNo, openDate, status, record.currentBalance )
    }

  }

}
