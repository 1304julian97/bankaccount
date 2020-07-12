package com.practice.bankaccount.application.facade

import com.practice.bankaccount.application.dto.{ BankAccountDTO, RestResponse }
import com.practice.bankaccount.domain.repository.AccountRepository

object BankAccountFacade {

  def getAllBanksAccounts( repository: AccountRepository ): RestResponse[List[BankAccountDTO]] = {
    BankAccountAppServicesFacade.getAllBankAccounts( repository )
  }

  def saveOrUpdateAccount( bankAccount: BankAccountDTO )( repository: AccountRepository ): RestResponse[String] = {
    BankAccountAppServicesFacade.saverUpdateAccount( bankAccount )( repository )
  }

}