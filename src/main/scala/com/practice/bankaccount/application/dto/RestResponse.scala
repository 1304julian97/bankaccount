package com.practice.bankaccount.application.dto

case class RestResponse[T](
  objectResponse:    Option[T],
  businessException: Option[String]
)
