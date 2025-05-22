package com.credit.simulator.creditsimulationservice.service

import com.credit.simulator.creditsimulationservice.domain.LoanSimulationRequest
import com.credit.simulator.creditsimulationservice.domain.LoanSimulationResult
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.time.LocalDate
import java.time.Period

@Service
class CreditSimulationService {

    fun simulateLoan(request: LoanSimulationRequest): LoanSimulationResult {
        val annualRate = getAnnualRate(request.dateOfBirth)
        val monthlyRate = annualRate.divide(BigDecimal(12), 10, RoundingMode.HALF_EVEN)

        val loanAmount = request.loanAmount
        val months = request.paymentTermMonths

        val numerator = monthlyRate.multiply(loanAmount)
        val denominator = BigDecimal.ONE - (BigDecimal.ONE + monthlyRate)
            .pow(-months, MathContext.DECIMAL64)

        val monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_EVEN)
        val totalAmount = monthlyPayment.multiply(BigDecimal(months)).setScale(2, RoundingMode.HALF_EVEN)
        val totalInterest = totalAmount.subtract(loanAmount).setScale(2, RoundingMode.HALF_EVEN)

        return LoanSimulationResult(totalAmount, monthlyPayment, totalInterest)
    }

    private fun getAnnualRate(dob: LocalDate): BigDecimal {
        val age = Period.between(dob, LocalDate.now()).years
        return when {
            age <= 25 -> BigDecimal("0.05")
            age <= 40 -> BigDecimal("0.03")
            age <= 60 -> BigDecimal("0.02")
            else -> BigDecimal("0.04")
        }
    }
}
