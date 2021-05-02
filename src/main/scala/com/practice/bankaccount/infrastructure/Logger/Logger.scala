package com.practice.bankaccount.infrastructure.Logger

trait Logger {

  def logError( message: String )( ex: Throwable ): Unit = {
    println( s"$message =======> ${ex.getMessage}" )
  }
  //import org.slf4j.LoggerFactory
  //LoggerFactory.getLogger( "Routes.class" ).error( s"Something was wrong, Use this code: $messageId", ex
  def log( message: String ): Unit = {
    println( message )
  }
}
