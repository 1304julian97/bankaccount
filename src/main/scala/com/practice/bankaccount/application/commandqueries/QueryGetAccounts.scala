package com.practice.bankaccount.application.commandqueries

import com.practice.bankaccount.application.PersitenceContext
import com.practice.bankaccount.application.dto.{ BankAccountDTO, RestResponse }
import com.practice.bankaccount.application.facade.BankAccountFacade

object QueryGetAccounts {

  def execute( context: PersitenceContext ): RestResponse[List[BankAccountDTO]] = {
    BankAccountFacade.getAllBanksAccounts( context.repositoryH2 )
  }

}
