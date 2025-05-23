package com.credit.simulator.creditsimulationservice.service

import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoRequest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class SimulacaoCreditoServiceTest {

    private val simulacaoCreditoService = SimulacaoCreditoService()

    @Test
    fun `Simulação de emprestimo para pessoas com idade de 30 anos`() {
        val dataNascimento = LocalDate.now().minusYears(30)
        val request = SimulacaoEmprestimoRequest(
            valorEmprestimo = BigDecimal("100.00"),
            quantidadeParcelas = 12,
            dataNascimento = dataNascimento
        )

        val result = simulacaoCreditoService.simulacaoEmprestimo(request)

        assertNotNull(result)
        assertEquals(10, result::class.members.size)
        assertFalse(result.totalJurosPago > BigDecimal("10000.00"))
        assertTrue(result.valorParcelaMensal > BigDecimal.ZERO)
    }

    @Test
    fun `simula emprestimo para pessoas com idades abaixo de 25`() {
        val dataNascimento = LocalDate.now().minusYears(25)
        val taxaAnual = simulacaoCreditoService.getTaxaAnual(dataNascimento)
        assertEquals(BigDecimal("0.05"), taxaAnual)
    }

    @Test
    fun `simula empréstimo para pessoas com idade entre 26 e 40`() {
        val dataNascimento = LocalDate.now().minusYears(26)
        val taxaAnual = simulacaoCreditoService.getTaxaAnual(dataNascimento)
        assertEquals(BigDecimal("0.03"), taxaAnual)
    }

    @Test
    fun `simula empréstimo para pessoas com idade entre 41 e 60`() {
        val dataNascimento = LocalDate.now().minusYears(42)
        val taxaAnual = simulacaoCreditoService.getTaxaAnual(dataNascimento)
        assertEquals(BigDecimal("0.02"), taxaAnual)
    }

    @Test
    fun `simula empréstimo para pessoas com idade acima de 60`() {
        val dataNascimento = LocalDate.now().minusYears(62)
        val taxaAnual = simulacaoCreditoService.getTaxaAnual(dataNascimento)
        assertEquals(BigDecimal("0.04"), taxaAnual)
    }

    @Test
    fun `Simula a taxa de juros conforme a data de nascimento`() {
        val dataNascimento = "1990-05-23"
        val taxaEsperada = BigDecimal("0.03")

        val taxa = simulacaoCreditoService.getTaxaAnual(dataNascimento)

        assertEquals(taxaEsperada, taxa)
    }
}