package com.practice.bankaccount.domain.model

sealed trait Status

case object ACTIVE extends Status
case object FROZEN extends Status
case object INACTIVE extends Status

object Status {

  def toString( status: Status ): String = status match {
    case ACTIVE   => "Active"
    case FROZEN   => "Frozen"
    case INACTIVE => "Inactive"
  }

  def fromString( status: String ): Option[Status] = status match {
    case "Active"   => Some( ACTIVE )
    case "Frozen"   => Some( FROZEN )
    case "Inactive" => Some( INACTIVE )
    case _          => None
  }

}