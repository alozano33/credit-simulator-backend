package com.credit.simulator.creditsimulationservice.domain

import java.math.BigDecimal

data class LoanSimulationResult(
    val totalAmount: BigDecimal,
    val monthlyInstallment: BigDecimal,
    val totalInterest: BigDecimal
)
