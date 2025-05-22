package com.credit.simulator.creditsimulationservice.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class FinancialCalculatorTest {

    @Test
    fun `calculateMonthlyPayment should return correct value for standard loan`() { // [cite: 10]
        val loanAmount = BigDecimal("10000.00")
        val annualInterestRate = 0.05
        val paymentTermMonths = 12
        val expectedMonthlyPayment = BigDecimal("856.07") // Calculated externally for verification
        val actualMonthlyPayment = FinancialCalculator.calculateMonthlyPayment(loanAmount, annualInterestRate, paymentTermMonths)
        assertEquals(expectedMonthlyPayment, actualMonthlyPayment)
    }

    @Test
    fun `calculateMonthlyPayment should handle zero interest rate`() { // [cite: 10]
        val loanAmount = BigDecimal("1200.00")
        val annualInterestRate = 0.0
        val paymentTermMonths = 12
        val expectedMonthlyPayment = BigDecimal("100.00")
        val actualMonthlyPayment = FinancialCalculator.calculateMonthlyPayment(loanAmount, annualInterestRate, paymentTermMonths)
        assertEquals(expectedMonthlyPayment, actualMonthlyPayment)
    }

    @Test
    fun `calculateTotalAmountPaid should return correct total`() { // [cite: 10]
        val monthlyPayment = BigDecimal("856.07")
        val paymentTermMonths = 12
        val expectedTotalAmount = BigDecimal("10272.84")
        val actualTotalAmount = FinancialCalculator.calculateTotalAmountPaid(monthlyPayment, paymentTermMonths)
        assertEquals(expectedTotalAmount, actualTotalAmount)
    }

    @Test
    fun `calculateTotalInterestPaid should return correct interest`() { // [cite: 10]
        val totalAmountPaid = BigDecimal("10272.84")
        val loanAmount = BigDecimal("10000.00")
        val expectedTotalInterest = BigDecimal("272.84")
        val actualTotalInterest = FinancialCalculator.calculateTotalInterestPaid(totalAmountPaid, loanAmount)
        assertEquals(expectedTotalInterest, actualTotalInterest)
    }
}