package com.practice.bankaccount.application.adapters.http

import com.practice.bankaccount.application.dto.ApplicationDto._
import com.practice.bankaccount.application.dto._
import io.circe._
import io.circe.generic.semiauto._

trait JsonDecoders {

  implicit val foundAccountDecoder: Decoder[BankAccountDto] = deriveDecoder[BankAccountDto]
  implicit val foundAccountEncoder: Encoder[BankAccountDto] = deriveEncoder[BankAccountDto]

  implicit val errorDecoder: Decoder[ErrorResponse] = deriveDecoder[ErrorResponse]
  implicit val errorEncoder: Encoder[ErrorResponse] = deriveEncoder[ErrorResponse]

  implicit val getStatusResponseDecoder: Decoder[GetStatusResponse] = deriveDecoder[GetStatusResponse]
  implicit val getStatusResponseEncoder: Encoder[GetStatusResponse] = deriveEncoder[GetStatusResponse]

  implicit val getAccountsResponseDecoder: Decoder[GetAccountsResponse] = deriveDecoder[GetAccountsResponse]
  implicit val getAccountsResponseEncoder: Encoder[GetAccountsResponse] = deriveEncoder[GetAccountsResponse]

  implicit val openAccountRequestDecoder: Decoder[OpenAccountRequest] = deriveDecoder[OpenAccountRequest]
  implicit val openAccountRequestEncoder: Encoder[OpenAccountRequest] = deriveEncoder[OpenAccountRequest]

  implicit val openAccountResponseDecoder: Decoder[OpenAccountResponse] = deriveDecoder[OpenAccountResponse]
  implicit val openAccountResponseEncoder: Encoder[OpenAccountResponse] = deriveEncoder[OpenAccountResponse]

}
