package com.practice.bankaccount.infrastructure.persistence.dao

import java.time.LocalDateTime

case class BankAccountDAO(
  accountNumber: Int,
  startDate:     LocalDateTime,
  status:        String,
  balance:       Int
)
