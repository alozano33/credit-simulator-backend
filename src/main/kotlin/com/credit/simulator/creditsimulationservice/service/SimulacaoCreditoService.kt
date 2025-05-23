package com.credit.simulator.creditsimulationservice.service

import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoRequest
import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoResult
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.time.LocalDate
import java.time.Period

@Service
class SimulacaoCreditoService {

    /**
     * Realiza a simulação do empréstimo com base nos valores, prazo e idade.
     *
     * @param request Objeto contendo os dados necessários para simulação (valor, prazo e idade)
     * @return Resultado da simulação contendo valor total, valor da parcela e juros totais
     */
    fun simulacaoEmprestimo(request: SimulacaoEmprestimoRequest): SimulacaoEmprestimoResult {
        val taxaAnual = getTaxaAnual(request.dataNascimento)
        val taxaMensal = taxaAnual.divide(BigDecimal(12), 10, RoundingMode.HALF_EVEN)

        val valorEmprestimo = request.valorEmprestimo
        val valorMensal = request.quantidadeParcelas

        val simulador = taxaMensal.multiply(valorEmprestimo)
        val calculadora = BigDecimal.ONE - (BigDecimal.ONE + taxaMensal)
            .pow(-valorMensal, MathContext.DECIMAL64)

        val parcelaMensal = simulador.divide(calculadora, 2, RoundingMode.HALF_EVEN)
        val pagamentoTotal = parcelaMensal.multiply(BigDecimal(valorMensal)).setScale(2, RoundingMode.HALF_EVEN)
        val jurosCobrado = pagamentoTotal.subtract(valorEmprestimo).setScale(2, RoundingMode.HALF_EVEN)

        return SimulacaoEmprestimoResult(pagamentoTotal, parcelaMensal, jurosCobrado)
    }

    /**
     * Obtém a taxa de juros anual baseada na data de nascimento fornecida como String.
     *
     * @param dataNascimento Data de nascimento no formato ISO (yyyy-MM-dd)
     * @return Taxa de juros anual como BigDecimal
     */
    fun getTaxaAnual(dataNascimento: String): BigDecimal {
        val taxa = LocalDate.parse(dataNascimento)
        return getTaxaAnual(taxa)
    }

    /**
     * Determina a taxa de juros anual com base na idade do solicitante.
     * As taxas são distribuídas da seguinte forma:
     * - Até 25 anos: 5%
     * - De 26 a 40 anos: 3%
     * - De 41 a 60 anos: 2%
     * - Acima de 60 anos: 4%
     *
     * @param taxa Data de nascimento como LocalDate
     * @return Taxa de juros anual correspondente à idade
     */
    fun getTaxaAnual(taxa: LocalDate): BigDecimal {
        val idade = Period.between(taxa, LocalDate.now()).years
        return when {
            idade <= 25 -> BigDecimal("0.05")
            idade <= 40 -> BigDecimal("0.03")
            idade <= 60 -> BigDecimal("0.02")
            else -> BigDecimal("0.04")
        }
    }
}