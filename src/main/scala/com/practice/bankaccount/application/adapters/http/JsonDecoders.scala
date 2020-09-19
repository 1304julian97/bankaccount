package com.practice.bankaccount.application.adapters.http

import com.practice.bankaccount.application.dto.{ BankAccountDTO, CreateAccountDTO, RestResponse, StatusDTO }
import io.circe._
import io.circe.generic.semiauto._

trait JsonDecoders {

  implicit val foundAccountDecoder: Decoder[BankAccountDTO] = deriveDecoder[BankAccountDTO]
  implicit val foundAccountEncoder: Encoder[BankAccountDTO] = deriveEncoder[BankAccountDTO]

  implicit val statusDecoder: Decoder[StatusDTO] = deriveDecoder[StatusDTO]
  implicit val statusEncoder: Encoder[StatusDTO] = deriveEncoder[StatusDTO]

  implicit val createAccountDecoder: Decoder[CreateAccountDTO] = deriveDecoder[CreateAccountDTO]
  implicit val createAccountEncoder: Encoder[CreateAccountDTO] = deriveEncoder[CreateAccountDTO]

  implicit val RestResponseDecoderList: Decoder[RestResponse[List[BankAccountDTO]]] = deriveDecoder[RestResponse[List[BankAccountDTO]]]
  implicit val RestResponseEncoderList: Encoder[RestResponse[List[BankAccountDTO]]] = deriveEncoder[RestResponse[List[BankAccountDTO]]]

  implicit val RestResponseDecoderString: Decoder[RestResponse[String]] = deriveDecoder[RestResponse[String]]
  implicit val RestResponseEncoderString: Encoder[RestResponse[String]] = deriveEncoder[RestResponse[String]]

}
