package com.credit.simulator.creditsimulationservice.service

import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoRequest
import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoResult
import com.credit.simulator.exception.InvalidDateFormatException
import com.credit.simulator.exception.ParcelasExcedemLimiteException
import com.credit.simulator.exception.ValorMenorQuePermitido
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeParseException

@Service
class SimulacaoCreditoService {

    /**
     * Realiza a simulação do empréstimo com base nos valores, prazo e idade.*/


    fun simulacaoEmprestimo(request: SimulacaoEmprestimoRequest): SimulacaoEmprestimoResult {

        val taxaAnual = try {
            getTaxaAnual(request.dataNascimento)
        } catch (e: DateTimeParseException) {
            throw InvalidDateFormatException("Formato de data inválido. Use o padrão yyyy-MM-dd.")
        }
        val quantidadeParcelas = request.quantidadeParcelas
        if (quantidadeParcelas > 48) {
            throw ParcelasExcedemLimiteException("A quantidade máxima de parcelas permitida é 48.")
        }
        val taxaMensal = taxaAnual.divide(BigDecimal(12), 10, RoundingMode.HALF_EVEN)
        val valorEmprestimo = request.valorEmprestimo
        if (valorEmprestimo < 1000.toBigDecimal()) {
            throw ValorMenorQuePermitido("valor menor que limite permitido. Valor: $valorEmprestimo")
        }

        val simulador = taxaMensal.multiply(valorEmprestimo)
        val calculadora = BigDecimal.ONE - (BigDecimal.ONE + taxaMensal)
            .pow(-quantidadeParcelas, MathContext.DECIMAL64)

        val parcelaMensal = simulador.divide(calculadora, 2, RoundingMode.HALF_EVEN)
        val pagamentoTotal = parcelaMensal.multiply(BigDecimal(quantidadeParcelas)).setScale(2, RoundingMode.HALF_EVEN)
        val jurosCobrado = pagamentoTotal.subtract(valorEmprestimo).setScale(2, RoundingMode.HALF_EVEN)

        return SimulacaoEmprestimoResult(pagamentoTotal, parcelaMensal, jurosCobrado)
    }

    /**
     * Valida e converte a data de nascimento, e calcula o cálculo da taxa.
     */
    fun getTaxaAnual(dataNascimento: String): BigDecimal {
        val taxa = LocalDate.parse(dataNascimento)
        return getTaxaAnual(taxa)
    }

    /**
     * Calcula a taxa com base na idade do cliente.
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