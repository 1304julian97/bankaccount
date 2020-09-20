package com.practice.bankaccount.domain.model

sealed trait Status

case object ACTIVE extends Status
case object FROZEN extends Status
case object INACTIVE extends Status