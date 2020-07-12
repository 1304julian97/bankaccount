
import domainservices.repository.Repository

class SavingAccountRepository extends Repository {
  val url: String = sys.env.getOrElse( "host", "localhost" )
  val port: Int = sys.env.getOrElse( "port", "5959" ).toInt
  val user: String = sys.env.getOrElse( "user", "julian" )
  val password: String = sys.env.get( "pass" ).getOrElse( throw new Error( "I do not have password in config file" ) )

  def saveOrUpdate( savingAccountRepository: BankAccountDAO ): String = {
    //DOCONECTION
    DBMessages.SUCCESS_ADD_SAVING_ACCOUNT
  }

  def listAll(): List[BankAccountDAO] = {

    List.empty
  }

}
