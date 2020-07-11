
import entities.{ BanckAccount, SavingsAccount }
import domainservices.repository.Repository

import scala.util.{ Failure, Success, Try }

class AccountServices {

  def saveUpdateAccount( savingsAccount: BanckAccount )( repository: Repository ): Either[Throwable, String] = {
    val savingsAccountDAO = savingsAccount match {
      case ba: SavingsAccount => MappersaDAO.convertSavingAccountEntityToDAO( ba )
      case _                  => throw new Error( "paila" )

    }
    val `return` = repository match {
      case sv: SavingAccountRepository => Try( sv.saveOrUpdate( savingsAccountDAO ) )
    }

    `return` match {
      case Success( s ) => Right( "Account saved with success" )
      case Failure( e ) => Left( e )

    }

  }

  def getListAccount( repository: Repository ): Either[Throwable, List[BanckAccount]] = {
    val acountsTry = repository match {
      case sv: SavingAccountRepository => Try( sv.listAll() )
    }

    acountsTry match {
      case Success( la ) => Right( la.map( x => MappersaDAO.convertBanckAccountDAOToSavingAccoutEntity( x ) ) )
      case Failure( e )  => Left( e )
    }
  }

}

