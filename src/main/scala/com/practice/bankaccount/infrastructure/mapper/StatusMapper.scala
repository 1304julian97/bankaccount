package com.practice.bankaccount.infrastructure.mapper

import com.practice.bankaccount.domain.model.{ ACTIVE, FROZEN, INACTIVE, Status }

object StatusMapper {

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
