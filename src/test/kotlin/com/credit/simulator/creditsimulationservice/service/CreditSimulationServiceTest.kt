package com.credit.simulator.creditsimulationservice.service

import com.credit.simulator.creditsimulationservice.domain.LoanSimulationRequest
import com.credit.simulator.customerservice.service.CustomerService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class CreditSimulationServiceTest {

    private val service = CreditSimulationService()

    @Test
    fun `simula empréstimo com idade entre 26 e 40`() {
        val request = LoanSimulationRequest(
            loanAmount = BigDecimal("10000"),
            dateOfBirth = LocalDate.now().minusYears(30),
            paymentTermMonths = 12
        )

        val result = service.simulateLoan(request)

        assertTrue(result.totalAmount > BigDecimal("10000"))
        assertEquals(result.totalAmount.setScale(2),
            result.monthlyInstallment.multiply(BigDecimal(12)).setScale(2))
    }
}