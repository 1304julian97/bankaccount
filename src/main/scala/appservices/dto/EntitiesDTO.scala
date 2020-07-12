package appservices.dto

import java.time.LocalDateTime

case class BankAccountDTO( accountNumber: Int, startDate: LocalDateTime, state: String, balance: Int )

