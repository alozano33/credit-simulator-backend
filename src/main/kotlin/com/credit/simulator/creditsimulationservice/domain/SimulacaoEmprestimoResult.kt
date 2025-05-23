package com.credit.simulator.creditsimulationservice.domain

import java.math.BigDecimal

/**
 * Classe de dados que representa o resultado de uma simulação de empréstimo.
 *
 * @property valorEmprestimo O valor total do empréstimo solicitado
 * @property valorParcelaMensal O valor da parcela mensal a ser paga
 * @property totalJurosPago O valor total dos juros a serem pagos durante todo o período do empréstimo
 */
data class SimulacaoEmprestimoResult(
    val valorEmprestimo: BigDecimal,
    val valorParcelaMensal: BigDecimal,
    val totalJurosPago: BigDecimal
)