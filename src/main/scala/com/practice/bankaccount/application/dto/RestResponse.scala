package com.practice.bankaccount.application.dto

case class RestResponse[T](
  objectResponse:    T,
  businessException: String
)
