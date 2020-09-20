package com.practice.bankaccount.infrastructure.persistence.inmemory

import java.time.LocalDateTime

case class BankAccountDAORecordInMemory(
  `type`:   String,
  number:   Int,
  openDate: LocalDateTime,
  status:   String,
  balance:  Int,
  rate:     Double
)
