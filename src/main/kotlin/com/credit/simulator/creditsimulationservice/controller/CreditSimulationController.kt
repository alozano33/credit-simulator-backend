package com.credit.simulator.creditsimulationservice.controller

import com.credit.simulator.creditsimulationservice.domain.LoanSimulationRequest
import com.credit.simulator.creditsimulationservice.domain.LoanSimulationResult
import com.credit.simulator.creditsimulationservice.service.CreditSimulationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/credit-simulations")
class CreditSimulationController(
    private val creditSimulationService: CreditSimulationService
) {
    @PostMapping
    fun simulateLoan(@RequestBody request: LoanSimulationRequest): ResponseEntity<LoanSimulationResult> {
        return ResponseEntity.ok(creditSimulationService.simulateLoan(request))
    }
}