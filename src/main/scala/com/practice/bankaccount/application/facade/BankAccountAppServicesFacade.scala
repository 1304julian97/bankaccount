package com.practice.bankaccount.application.facade

import com.practice.bankaccount.application.dto.{ BankAccountDTO, MappersDTO, RestResponse }
import com.practice.bankaccount.application.service.AccountService
import com.practice.bankaccount.domain.repository.AccountRepository

object BankAccountAppServicesFacade {

  val accountService = new AccountService()

  def getAllBankAccounts( repository: AccountRepository ): RestResponse[List[BankAccountDTO]] = {
    accountService.getListAccount( repository ) match {
      case Right( list ) => RestResponse( Option( list.map( x => MappersDTO.convertBankAccountEntityToDTO( x ) ) ), None )
      case Left( e )     => RestResponse( None, Option( e.getMessage ) )
    }

  }

  def saverUpdateAccount( bankAccountDTO: BankAccountDTO )( repository: AccountRepository ): RestResponse[String] = {
    accountService.saveUpdateAccount( MappersDTO.convertBankAccountDTOToSavingAccoutEntity( bankAccountDTO ) )( repository ) match {
      case Right( s ) => RestResponse( Option( s ), None )
      case Left( e )  => RestResponse( None, Option( e.getMessage ) )
    }

  }

}
