package com.credit.simulator.creditsimulationservice.domain

import java.math.BigDecimal
import java.time.LocalDate

data class SimulacaoEmprestimoRequest(
    val valorEmprestimo: BigDecimal,
    val dataNascimento: LocalDate,
    val quantidadeParcelas: Int
)