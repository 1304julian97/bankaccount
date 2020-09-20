package com.practice.bankaccount.infrastructure.persistence.h2

import com.practice.bankaccount.infrastructure.persistence.h2.table.AccountTableH2
import slick.lifted.TableQuery

object H2Tables {

  val bankAccounts = TableQuery[AccountTableH2]

}
