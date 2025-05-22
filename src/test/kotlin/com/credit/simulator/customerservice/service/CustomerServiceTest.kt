package com.credit.simulator.customerservice.service

import com.credit.simulator.customerservice.service.CustomerService
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    private val customerService = CustomerService()

    @Test
    fun `getAge should return correct age`() {
        val dateOfBirth = LocalDate.of(1990, 5, 15)
        val age = customerService.getAge(dateOfBirth)
        // Adjust assertion based on current date for robust testing
        val expectedAge = LocalDate.now().year - dateOfBirth.year -
                if (LocalDate.now().monthValue < dateOfBirth.monthValue ||
                    (LocalDate.now().monthValue == dateOfBirth.monthValue && LocalDate.now().dayOfMonth < dateOfBirth.dayOfMonth)) 1 else 0
        assertEquals(expectedAge, age)
    }

    @Test
    fun `getInterestRateByAge should return 5 percent for age up to 25`() { // [cite: 9]
        assertEquals(0.05, customerService.getInterestRateByAge(20))
        assertEquals(0.05, customerService.getInterestRateByAge(25))
    }

    @Test
    fun `getInterestRateByAge should return 3 percent for age between 26 and 40`() { // [cite: 9]
        assertEquals(0.03, customerService.getInterestRateByAge(26))
        assertEquals(0.03, customerService.getInterestRateByAge(35))
        assertEquals(0.03, customerService.getInterestRateByAge(40))
    }

    @Test
    fun `getInterestRateByAge should return 2 percent for age between 41 and 60`() { // [cite: 9]
        assertEquals(0.02, customerService.getInterestRateByAge(41))
        assertEquals(0.02, customerService.getInterestRateByAge(50))
        assertEquals(0.02, customerService.getInterestRateByAge(60))
    }

    @Test
    fun `getInterestRateByAge should return 4 percent for age above 60`() { // [cite: 9]
        assertEquals(0.04, customerService.getInterestRateByAge(61))
        assertEquals(0.04, customerService.getInterestRateByAge(70))
    }
}