package com.practice.bankaccount.application.commandqueries

import com.practice.bankaccount.application.PersitenceContext
import com.practice.bankaccount.application.dto.{ BankAccountDTO, RestResponse }
import com.practice.bankaccount.application.facade.BankAccountFacade

object CommanUpsertAccounts {

  def execute( bankAccount: BankAccountDTO )( context: PersitenceContext ): RestResponse[String] = {
    BankAccountFacade.saveOrUpdateAccount( bankAccount )( context.repositoryH2 )
  }

}
