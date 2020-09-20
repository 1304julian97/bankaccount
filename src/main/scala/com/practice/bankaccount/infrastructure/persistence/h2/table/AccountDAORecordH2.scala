package com.practice.bankaccount.infrastructure.persistence.h2.table

import java.sql.Timestamp

case class AccountDAORecordH2(
  accountNo:      Int,
  accountType:    String,
  dateOfOpen:     Timestamp,
  accountStatus:  String,
  currentBalance: Int,
  accountRate:    Double
)