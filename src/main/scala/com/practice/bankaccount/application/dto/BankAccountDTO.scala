package com.practice.bankaccount.application.dto

import java.time.LocalDateTime

case class BankAccountDTO(
  accountNumber: Int,
  startDate:     String,
  status:        String,
  balance:       Int
)
