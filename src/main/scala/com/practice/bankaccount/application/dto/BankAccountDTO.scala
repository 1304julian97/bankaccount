package com.practice.bankaccount.application.dto

import java.time.LocalDateTime

case class BankAccountDTO(
  accountNumber: Int,
  startDate:     LocalDateTime,
  status:        String,
  balance:       Int
)
