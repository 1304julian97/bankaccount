package com.practice.bankaccount.application.facade

import com.practice.bankaccount.application.dto.{ BankAccountDTO, MappersDTO, RestResponse }
import com.practice.bankaccount.application.service.AccountService
import com.practice.bankaccount.domain.repository.AccountRepository

object BankAccountAppServicesFacade {

  val accountService = new AccountService()

  def getAllBankAccounts( repository: AccountRepository ): RestResponse[List[BankAccountDTO]] = {
    accountService.getListAccount( repository ) match {
      case Right( list ) => RestResponse( list.map( x => MappersDTO.convertBankAccountEntityToDTO( x ) ), null )
      case Left( e )     => RestResponse( null, e.getMessage )
    }

  }

  def saverUpdateAccount( bankAccountDTO: BankAccountDTO )( repository: AccountRepository ): RestResponse[String] = {
    accountService.saveUpdateAccount( MappersDTO.convertBankAccountDTOToSavingAccoutEntity( bankAccountDTO ) )( repository ) match {
      case Right( s ) => RestResponse( s, null )
      case Left( e )  => RestResponse( null, e.getMessage )
    }

  }

}
