package com.credit.simulator.creditsimulationservice.controller

import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoRequest
import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoResult
import com.credit.simulator.creditsimulationservice.service.SimulacaoCreditoService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import org.springframework.http.HttpStatus
import java.time.LocalDate

class ValorSolicitadoControllerTest {

    private val simulacaoCreditoService = mock(SimulacaoCreditoService::class.java)
    private val controller = ValorSolicitadoController(simulacaoCreditoService)

    @Test
    fun `Simulação de Emprestimo para Pessoas com 30 anos`() {
        val dataNascimento = LocalDate.now().minusYears(30).toString()
        val request = SimulacaoEmprestimoRequest(
            valorEmprestimo = BigDecimal("10000.00"),
            quantidadeParcelas = 12,
            dataNascimento = dataNascimento.let { LocalDate.parse(it) }
        )

        val resultadoSimulacao = SimulacaoEmprestimoResult(
            valorEmprestimo = BigDecimal("10600.00"),
            valorParcelaMensal = BigDecimal("883.33"),
            totalJurosPago = BigDecimal("600.00")
        )

        `when`(simulacaoCreditoService.simulacaoEmprestimo(request)).thenReturn(resultadoSimulacao)

        val response = controller.valorSolicitado(request)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(resultadoSimulacao, response.body)
        verify(simulacaoCreditoService).simulacaoEmprestimo(request)
    }

    @Test
    fun `Simua taxa de juros pessoas com 45 anos `() {
        val dataNascimento = LocalDate.now().minusYears(45).toString()
        val taxa = BigDecimal("0.02")

        `when`(simulacaoCreditoService.getTaxaAnual(dataNascimento)).thenReturn(taxa)

        val response = controller.getTaxaAnual(dataNascimento)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(dataNascimento, response.body!!["dataNascimento"])
        assertEquals(taxa, response.body!!["juros"])

        verify(simulacaoCreditoService).getTaxaAnual(dataNascimento)
    }
}
