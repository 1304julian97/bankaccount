package com.practice.bankaccount.domain.service

import com.practice.bankaccount.domain.model.{ AccountStatus, BankAccount, CheckingAccount, SavingsAccount }
import com.practice.bankaccount.domain.repository.AccountRepository
import com.practice.bankaccount.infrastructure.persistence.dao.BankAccountMapper

object AccountService {

  def openAccount( number: Int, balance: Int, accountType: String )( repository: AccountRepository ): Either[String, BankAccount] = {
    if ( accountType != "S" && accountType != "C" ) {
      Left( "Invalid account type" )
    } else {
      /*val result: Either[String, BankAccount] = accountType match {
        case "S" => BankAccount.createSavingsAccount( number, balance )
        case "C" => BankAccount.createCheckingAccount( number, balance )
      }

      result match {
        case Left( error )    => Left( error )
        case Right( account ) => repository.upsert( account )
      }*/

      for {
        createdAccount <- {
          accountType match {
            case "S" => BankAccount.createSavingsAccount( number, balance )
            case "C" => BankAccount.createCheckingAccount( number, balance )
          }
        }
        resultUpsert <- repository.upsert( createdAccount match {
          case ch: CheckingAccount => BankAccountMapper.convertCheckingAccountEntityToDAO( ch )
          case sa: SavingsAccount  => BankAccountMapper.convertSavingAccountEntityToDAO( sa )
        } )
      } yield createdAccount

    }

  }

  def listAllAcounts()( repository: AccountRepository ): Either[String, List[BankAccount]] = {
    repository.list() match {
      case Right( list ) => Right( list.map( b => BankAccountMapper.convertBankAccountDAOToCheckingAccoutEntity( b ) ) )
      case Left( s )     => Left( s )
    }
  }

}
