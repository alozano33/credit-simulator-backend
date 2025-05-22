package com.credit.simulator.creditsimulationservice.domain

import java.math.BigDecimal
import java.time.LocalDate

data class LoanSimulationRequest(
    val loanAmount: BigDecimal,
    val dateOfBirth: LocalDate,
    val paymentTermMonths: Int
)
