import java.time.LocalDateTime

import domainservices.repository.Repository
import entities.{ AccountState, BanckAccount, SavingsAccount }
import org.scalactic.Fail
import org.scalatest.{ Outcome, fixture }


class Testdrive extends fixture.FunSuite {

  override protected def withFixture( test: OneArgTest ): Outcome = {
    println( "Hi!! I am a fixture" )
    val repositorySuccess: Repository = new SavingAccountRepositoryTestSuccess
    val repositoryFailed: Repository = new SavingAccountRepositoryTestFailed
    val accountServices: AccountServices = new AccountServices()

    test( accountServices, repositorySuccess, repositoryFailed )
  }

  override type FixtureParam = ( AccountServices, Repository, Repository )

  test( "saveUpdateAccount success response" ) { f =>
    println( f )
    val banckAccount = SavingsAccount( 12121212, LocalDateTime.now, AccountState.ACTIVATED, 45656 )
    val currentResponse: Either[Throwable, String] = f._1.saveUpdateAccount( banckAccount )( f._2 )

    currentResponse match {
      case Right( s ) => assertResult( DBMessages.SUCCESS_ADD_SAVING_ACCOUNT )( s )
      case _          => Fail( "saveUpdateAccount should be Right and this one was Left" )
    }
  }

  test( "saveUpdateAccount failed response" ) { f =>
    println( f )
    val banckAccount = SavingsAccount( 12121212, LocalDateTime.now, AccountState.ACTIVATED, 45656 )
    val currentResponse: Either[Throwable, String] = f._1.saveUpdateAccount( banckAccount )( f._3 )

    currentResponse match {
      case Left( e ) => assertResult( "Update Failed." )( e.getMessage )
      case _         => Fail( "saveUpdateAccount should be Left and this one was Righ" )
    }
  }

  test( "getListAccount success response" ) { f =>
    val currentResponse: Either[Throwable, List[BanckAccount]] = f._1.getListAccount( f._2 )
    val accountExpected1 = BankAccountDAO( 12121212, LocalDateTime.now, "ACTIVATED", 45454545 )
    val accountExpected2 = BankAccountDAO( 12121213, LocalDateTime.now, "FROZEN", 454454545 )

    currentResponse match {
      case Right( list ) => assertResult( List( accountExpected1, accountExpected2 ) )( list )
      case _             => Fail( "saveUpdateAccount should be Right and this one was Left" )
    }

  }

  test( "getListAccount failed response" ) { f =>
    val currentResponse: Either[Throwable, List[BanckAccount]] = f._1.getListAccount( f._3 )

    currentResponse match {
      case Left( e ) => assertResult( "Fail to search accounts." )( e.getMessage )
      case _         => Fail( "saveUpdateAccount should be Left and this one was Right" )
    }

  }

}

class SavingAccountRepositoryTestSuccess extends Repository {

  val url: String = sys.env.getOrElse( "host", "localhost" )
  val port: Int = sys.env.getOrElse( "port", "5959" ).toInt
  val user: String = sys.env.getOrElse( "user", "julian" )
  val password: String = sys.env.getOrElse( "pass", "test" )

  def saveOrUpdate( savingAccountTest: BankAccountDAO ): String = {
    DBMessages.SUCCESS_ADD_SAVING_ACCOUNT
  }

  def listAll: List[BankAccountDAO] = {
    val account1: BankAccountDAO = BankAccountDAO( 12121212, LocalDateTime.now, "ACTIVATED", 45454545 )
    val account2: BankAccountDAO = BankAccountDAO( 12121213, LocalDateTime.now, "FROZEN", 454454545 )
    List( account1, account2 )
  }
}

class SavingAccountRepositoryTestFailed extends Repository {

  val url: String = sys.env.getOrElse( "host", "localhost" )
  val port: Int = sys.env.getOrElse( "port", "5959" ).toInt
  val user: String = sys.env.getOrElse( "user", "julian" )
  val password: String = sys.env.getOrElse( "pass", "test" )

  def saveOrUpdate( savingAccountTest: BankAccountDAO ): String = {
    throw new Exception( "Update Failed." )
  }

  def listAll: List[BankAccountDAO] = {
    throw new Exception( "Fail to search accounts." )
  }
}