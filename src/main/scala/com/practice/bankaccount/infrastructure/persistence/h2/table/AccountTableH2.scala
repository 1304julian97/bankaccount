package com.practice.bankaccount.infrastructure.persistence.h2.table

import java.sql.Timestamp
import slick.jdbc.H2Profile.api._

class AccountTableH2( tag: Tag ) extends Table[AccountDAORecordH2]( tag, "bank_accounts" ) {

  def accountNo = column[Int]( "accountNo", O.PrimaryKey )
  def accountType = column[String]( "accountType" )
  def dateOfOpen = column[Timestamp]( "dateOfOpen" )
  def accountStatus = column[String]( "accountStatus" )
  def currentBalance = column[Int]( "currentBalance" )
  def accountRate = column[Double]( "accountRate" )

  def * = ( accountNo, accountType, dateOfOpen, accountStatus, currentBalance, accountRate ) <> ( AccountDAORecordH2.tupled, AccountDAORecordH2.unapply )

}