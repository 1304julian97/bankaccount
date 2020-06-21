
import entities.AccountState.AccountState
import entities.{ AccountState, CheckingAccount, SavingsAccount }

object MappersaDAO {

  def convertBanckAccountDAOToSavingAccoutEntity( dto: BankAccountDAO ): SavingsAccount = {
    val accountState = convertAccountStateStringToEnum( dto.state )
    SavingsAccount( dto.accountNumber, dto.startDate, accountState, dto.balance )
  }

  def convertBanckAccountDAOToCheckingAccoutEntity( dto: BankAccountDAO ): CheckingAccount = {
    val accountState = convertAccountStateStringToEnum( dto.state )
    CheckingAccount( dto.accountNumber, dto.startDate, accountState, dto.balance )
  }

  def convertSavingAccountEntityToDAO( entity: SavingsAccount ): BankAccountDAO = {
    val accountState = convertAccountStateEnumToString( entity.state )
    BankAccountDAO( entity.accountNumber, entity.startDate, accountState, entity.balance )
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
