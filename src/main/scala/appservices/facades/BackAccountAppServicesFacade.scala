import appservices.dto.BankAccountDTO
import domainservices.repository.Repository

object BanckAccountFacade {

  val accoundService = new AccountServices()

  def getAllBancksAccount( repository: Repository ): RestResponse[List[BankAccountDTO]] = {
    accoundService.getListAccount( repository ) match {
      case Right( list ) => RestResponse( list.map( x => MappersDTO.convertBanckAccountEntityToDTO( x ) ), null )
      case Left( e )     => RestResponse( null, e.getMessage )
    }

  }

  def saveorUpdateAccount( banckAccountDTO: BankAccountDTO )( repository: Repository ): RestResponse[String] = {
    accoundService.saveUpdateAccount( MappersDTO.convertBanckAccountDTOToSavingAccoutEntity( banckAccountDTO ) )( repository ) match {
      case Right( s ) => RestResponse( s, null )
      case Left( e )  => RestResponse( null, e.getMessage )
    }

  }

}
