package com.credit.simulator.customerservicetest.service

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

@Service
class CustomerService {

    fun getAge(dateOfBirth: LocalDate): Int {
        return Period.between(dateOfBirth, LocalDate.now()).years
    }

    fun getInterestRateByAge(age: Int): Double { // [cite: 9]
        return when {
            age <= 25 -> 0.05 // 5% ao ano [cite: 9]
            age in 26..40 -> 0.03 // 3% ao ano [cite: 9]
            age in 41..60 -> 0.02 // 2% ao ano [cite: 9]
            else -> 0.04 // 4% ao ano [cite: 9] (Acima de 60 anos)
        }
    }
}