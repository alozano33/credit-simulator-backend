package com.credit.simulator.creditsimulationservice.controller

import com.credit.simulator.CreditSimulatorBackendApplication
import com.credit.simulator.creditsimulationservice.domain.LoanSimulationRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest(classes = [CreditSimulatorBackendApplication::class])
@AutoConfigureMockMvc
class CreditSimulationControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule()) // Register module for LocalDate serialization/deserialization
    }

    @Test
    fun `simulateLoan endpoint should return correct simulation result`() { // [cite: 7, 10]
        val request = LoanSimulationRequest(
            loanAmount = BigDecimal("10000.00"),
            dateOfBirth = LocalDate.of(1995, 5, 20), // Age <= 25, so 5% interest
            paymentTermMonths = 12
        )

        val expectedMonthlyPayment = BigDecimal("856.07") // Calculated with 5% annual interest
        val expectedTotalAmountPaid = BigDecimal("10272.84")
        val expectedTotalInterestPaid = BigDecimal("272.84")

        mockMvc.post("/api/v1/credit-simulations") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.monthlyPayment") { value(expectedMonthlyPayment) }
            jsonPath("$.totalAmountPaid") { value(expectedTotalAmountPaid) }
            jsonPath("$.totalInterestPaid") { value(expectedTotalInterestPaid) }
        }.andReturn()
    }

    @Test
    fun `simulateLoan endpoint should handle different age groups correctly`() { // [cite: 9]
        // Age between 26-40 (3% interest)
        val requestMiddleAge = LoanSimulationRequest(
            loanAmount = BigDecimal("10000.00"),
            dateOfBirth = LocalDate.of(1985, 5, 20), // Age between 26 and 40 (assuming current year 2025)
            paymentTermMonths = 12
        )
        val expectedMonthlyPaymentMiddleAge = BigDecimal("846.94") // Calculated with 3% annual interest

        mockMvc.post("/api/v1/credit-simulations") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestMiddleAge)
        }.andExpect {
            status { isOk() }
            jsonPath("$.monthlyPayment") { value(expectedMonthlyPaymentMiddleAge) }
        }

        // Age above 60 (4% interest)
        val requestOldAge = LoanSimulationRequest(
            loanAmount = BigDecimal("10000.00"),
            dateOfBirth = LocalDate.of(1960, 5, 20), // Age > 60 (assuming current year 2025)
            paymentTermMonths = 12
        )
        val expectedMonthlyPaymentOldAge = BigDecimal("852.12") // Calculated with 4% annual interest

        mockMvc.post("/api/v1/credit-simulations") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestOldAge)
        }.andExpect {
            status { isOk() }
            jsonPath("$.monthlyPayment") { value(expectedMonthlyPaymentOldAge) }
        }
    }
}