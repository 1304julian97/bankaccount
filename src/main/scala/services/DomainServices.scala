package services

import entities.{BanckAccount, SavingsAccount}
import services.repository.Repository



class AccountServices {

  def saveUpdateAccount(savingsAccountServices: SavingsAccount)(repository: Repository):Either[Throwable,String]={
    Right("Hi!")
  }

  def listAccount(repository: Repository):Either[Throwable,List[BanckAccount]]={

    Right(List.empty)
  }

}
