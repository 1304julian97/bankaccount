package com.practice.bankaccount.infrastructure.persistence.h2.table

import slick.lifted.TableQuery

object H2Tables {

  val bankAccounts = TableQuery[AccountTableH2]

}
