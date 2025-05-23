package com.credit.simulator.creditsimulationservice.controller

import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoRequest
import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoResult
import com.credit.simulator.creditsimulationservice.service.SimulacaoCreditoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Controller responsável por gerenciar as operações relacionadas à simulação de crédito.
 * Fornece endpoints para simulação de empréstimo e avaliação de taxa de juros.
 */
@RestController
@RequestMapping("/api/v1/simulacao-credito")
class ValorSolicitadoController(
    private val simulacaoCreditoService: SimulacaoCreditoService
) {

    /**
     * Realiza a simulação de empréstimo com base nos dados fornecidos.
     *
     * @param request Objeto contendo os dados necessários para simulação do empréstimo
     * @return ResponseEntity contendo o resultado da simulação do empréstimo
     */
    @PostMapping("/simulacao")
    fun valorSolicitado(@RequestBody request: SimulacaoEmprestimoRequest): ResponseEntity<SimulacaoEmprestimoResult> {
        return ResponseEntity.ok(simulacaoCreditoService.simulacaoEmprestimo(request))
    }

    /**
     * Obtém a taxa de juros anual com base na data de nascimento do cliente.
     *
     * @param dataNascimento Data de nascimento do cliente no formato string
     * @return ResponseEntity contendo um mapa com a data de nascimento e a taxa de juros calculada
     */
    @GetMapping("/avaliacao")
    fun getTaxaAnual(@RequestParam("dataNascimento") dataNascimento: String): ResponseEntity<Map<String, Any>> {
        val juros = simulacaoCreditoService.getTaxaAnual(dataNascimento)
        return ResponseEntity.ok(mapOf("dataNascimento" to dataNascimento, "juros" to juros))
    }
}