package com.credit.simulator.creditsimulationservice.domain

import java.math.BigDecimal
import java.time.LocalDate

/**
 * Classe de dados (data class) que representa uma requisição de simulação de empréstimo.
 * 
 * @property valorEmprestimo O valor do empréstimo solicitado em BigDecimal
 * @property dataNascimento A data de nascimento do solicitante
 * @property quantidadeParcelas O número de parcelas desejado para o empréstimo
 */
data class SimulacaoEmprestimoRequest(
    val valorEmprestimo: BigDecimal,
    val dataNascimento: LocalDate,
    val quantidadeParcelas: Int
)