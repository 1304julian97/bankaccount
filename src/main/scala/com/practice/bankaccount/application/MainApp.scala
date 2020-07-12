package com.practice.bankaccount.application

import com.typesafe.config.{ Config, ConfigFactory }

object MainApp extends App {

  private val config: Config = ConfigFactory.load()
  private val name: String = config.getString( "app.name" )

  println( s"Running application $name" )

}