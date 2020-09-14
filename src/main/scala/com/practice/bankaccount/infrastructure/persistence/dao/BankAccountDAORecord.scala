package com.practice.bankaccount.infrastructure.persistence.dao

import java.time.LocalDateTime

case class BankAccountDAORecord(
  `type`:   String,
  number:   Int,
  openDate: LocalDateTime,
  status:   String,
  balance:  Int,
  rate:     Double
)
