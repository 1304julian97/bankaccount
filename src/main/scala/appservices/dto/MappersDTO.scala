import appservices.dto.BankAccountDTO
import entities.AccountState.AccountState
import entities.{ AccountState, BanckAccount, CheckingAccount, SavingsAccount }

object MappersDTO {

  def convertBanckAccountDTOToSavingAccoutEntity( dto: BankAccountDTO ): SavingsAccount = {
    val accountState = convertAccountStateStringToEnum( dto.state )
    SavingsAccount( dto.accountNumber, dto.startDate, accountState, dto.balance )
  }

  def convertBanckAccountDTOToCheckingAccoutEntity( dto: BankAccountDTO ): CheckingAccount = {
    val accountState = convertAccountStateStringToEnum( dto.state )
    CheckingAccount( dto.accountNumber, dto.startDate, accountState, dto.balance )
  }

  def convertBanckAccountEntityToDTO( entity: BanckAccount ): BankAccountDTO = {
    val accountState = convertAccountStateEnumToString( entity.state )
    BankAccountDTO( entity.accountNumber, entity.startDate, accountState, entity.balance )
  }

  def convertAccountStateEnumToString( accountState: AccountState ): String = {
    accountState match {
      case AccountState.BLOCKED   => "BLOCKED"
      case AccountState.FROZEN    => "FROZEN"
      case AccountState.ACTIVATED => "ACTIVATED"
    }
  }

  def convertAccountStateStringToEnum( accountState: String ): AccountState = {
    accountState match {
      case "BLOCKED"   => AccountState.BLOCKED
      case "FROZEN"    => AccountState.FROZEN
      case "ACTIVATED" => AccountState.ACTIVATED
    }
  }
}
