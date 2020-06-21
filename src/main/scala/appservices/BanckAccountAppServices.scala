import appservices.dto.BankAccountDTO
import domainservices.repository.Repository

object BanckAccountAppServices {
  def getAllBancksAccount( repository: Repository ): RestResponse[List[BankAccountDTO]] = {

    BanckAccountFacade.getAllBancksAccount( repository )

  }

  def saveorUpdateAccount( banckAccount: BankAccountDTO )( repository: Repository ): RestResponse[String] = {

    BanckAccountFacade.saveorUpdateAccount( banckAccount )( repository )
  }
}