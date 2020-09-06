package com.practice.bankaccount.application.adapters.http

import com.practice.bankaccount.application.dto.BankAccountDTO
import io.circe._
import io.circe.generic.semiauto._

trait JsonDecoders {

  implicit val foundAccountDecoder: Decoder[BankAccountDTO] = deriveDecoder[BankAccountDTO]
  implicit val foundAccountEncoder: Encoder[BankAccountDTO] = deriveEncoder[BankAccountDTO]

}
