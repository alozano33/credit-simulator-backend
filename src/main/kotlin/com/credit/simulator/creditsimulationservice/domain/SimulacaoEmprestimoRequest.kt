package com.credit.simulator.creditsimulationservice.domain

import java.math.BigDecimal
import java.time.LocalDate

/**
 * Classe de requisição para simulação de empréstimo.
 *
 * @property valorEmprestimo Valor do empréstimo solicitado
 * @property dataNascimento Data de nascimento do solicitante
 * @property quantidadeParcelas Número de parcelas desejadas para o empréstimo
 */
data class SimulacaoEmprestimoRequest(
    val valorEmprestimo: BigDecimal,
    val dataNascimento: LocalDate,
    val quantidadeParcelas: Int
)