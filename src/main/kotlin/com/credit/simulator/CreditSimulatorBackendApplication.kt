package com.credit.simulator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CreditSimulatorBackendApplication

fun main(args: Array<String>) {
    runApplication<CreditSimulatorBackendApplication>(*args)
}
