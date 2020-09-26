package com.practice.bankaccount.application.dto

object ApplicationDto {

  case class BankAccountDto(
    accountNumber: Int,
    OpenDate:      String,
    status:        String,
    balance:       Int
  )

  case class ErrorResponse(
    dateTime: String,
    message:  String
  )

  case class GetAccountsResponse(
    dateTime: String,
    accounts: List[BankAccountDto]
  )

  case class GetStatusResponse(
    dateTime: String,
    message:  String
  )

  case class OpenAccountRequest(
    number:      Int,
    balance:     Int,
    accountType: String
  )

  case class OpenAccountResponse(
    dateTime:    String,
    bankAccount: BankAccountDto
  )

}
