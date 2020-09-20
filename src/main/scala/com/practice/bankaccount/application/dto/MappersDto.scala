package com.practice.bankaccount.application.dto

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.practice.bankaccount.application.dto.ApplicationDto.BankAccountDto
import com.practice.bankaccount.domain.model.{ BankAccount, CheckingAccount, SavingsAccount, Status }
import com.practice.bankaccount.infrastructure.mapper.StatusMapper

trait MappersDto {

  private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" )

  def mapBankAccountToDTO( bankAccount: BankAccount ): BankAccountDto = {
    val status = StatusMapper.toString( bankAccount.status )
    val dateTime = formatter.format( bankAccount.openDate )
    BankAccountDto( bankAccount.number, dateTime, status, bankAccount.balance )
  }

}
