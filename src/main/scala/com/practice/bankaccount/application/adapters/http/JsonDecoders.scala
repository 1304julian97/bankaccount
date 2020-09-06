package com.practice.bankaccount.application.adapters.http

import com.practice.bankaccount.application.dto.{ BankAccountDTO, CreateAccountDTO, StatusDTO }
import io.circe._
import io.circe.generic.semiauto._

trait JsonDecoders {

  implicit val foundAccountDecoder: Decoder[BankAccountDTO] = deriveDecoder[BankAccountDTO]
  implicit val foundAccountEncoder: Encoder[BankAccountDTO] = deriveEncoder[BankAccountDTO]

  implicit val statusDecoder: Decoder[StatusDTO] = deriveDecoder[StatusDTO]
  implicit val statusEncoder: Encoder[StatusDTO] = deriveEncoder[StatusDTO]

  implicit val createAccountDecoder: Decoder[CreateAccountDTO] = deriveDecoder[CreateAccountDTO]
  implicit val createAccountEncoder: Encoder[CreateAccountDTO] = deriveEncoder[CreateAccountDTO]

}
