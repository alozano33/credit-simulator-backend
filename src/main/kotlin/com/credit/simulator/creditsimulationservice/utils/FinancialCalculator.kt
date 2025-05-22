package com.credit.simulator.creditsimulationservice.utils

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

object FinancialCalculator {

    /**
     * Calcula o pagamento mensal de um empréstimo com parcelas fixas.
     * Fórmula: PMT = (PV * r) / (1 - (1 + r)^-n) [cite: 10]
     * Onde:
     * PMT = Pagamento mensal [cite: 10]
     * PV = Valor presente (empréstimo) [cite: 10]
     * r = Taxa de juros mensal (taxa anual / 12) [cite: 10]
     * n = Número total de pagamentos (meses) [cite: 10]
     */
    fun calculateMonthlyPayment(
        loanAmount: BigDecimal,
        annualInterestRate: Double,
        paymentTermMonths: Int
    ): BigDecimal {
        if (annualInterestRate == 0.0) {
            return loanAmount.divide(paymentTermMonths.toBigDecimal(), 2, RoundingMode.HALF_UP)
        }

        val monthlyInterestRate = annualInterestRate / 12.0 // [cite: 10]
        val numerator = loanAmount.multiply(monthlyInterestRate.toBigDecimal())
        val denominator = BigDecimal(1.0 - (1.0 + monthlyInterestRate).pow(-paymentTermMonths))

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP)
    }

    fun calculateTotalAmountPaid(monthlyPayment: BigDecimal, paymentTermMonths: Int): BigDecimal {
        return monthlyPayment.multiply(paymentTermMonths.toBigDecimal()).setScale(2, RoundingMode.HALF_UP)
    }

    fun calculateTotalInterestPaid(totalAmountPaid: BigDecimal, loanAmount: BigDecimal): BigDecimal {
        return totalAmountPaid.subtract(loanAmount).setScale(2, RoundingMode.HALF_UP)
    }
}