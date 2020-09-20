package com.practice.bankaccount.infrastructure.persistence.dao

import com.practice.bankaccount.domain.model.{ BankAccount, CheckingAccount, SavingsAccount, Status }
import com.practice.bankaccount.infrastructure.mapper.StatusMapper

trait BankAccountDAOMapper {

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

  def mapBankAccountToDAORecord( bankAccoun: BankAccount ): Either[String, BankAccountDAORecord] = {
    val accountType: String = bankAccoun match {
      case acc: SavingsAccount  => "Savings"
      case acc: CheckingAccount => "Checking"
    }
    val rate: Double = bankAccoun match {
      case acc: SavingsAccount => acc.rate
      case _                   => 0.0
    }
    val status: String = StatusMapper.toString( bankAccoun.status )

    Right( BankAccountDAORecord( accountType, bankAccoun.number, bankAccoun.openDate, status, bankAccoun.balance, rate ) )
  }

  def mapDAORecordToBankAccount( record: BankAccountDAORecord ): Either[String, BankAccount] = {
    for {
      status <- validateStatus( record.status )
      accType <- validateType( record.`type` )
    } yield {
      if ( accType == "Savings" ) SavingsAccount( record.number, record.openDate, status, record.balance, record.rate )
      else CheckingAccount( record.number, record.openDate, status, record.balance )
    }
  }

}
