package com.credit.simulator.creditsimulationservice.integration

import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoRequest
import com.credit.simulator.creditsimulationservice.domain.SimulacaoEmprestimoResult
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValorSolicitadoControllerIntegration {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `Simulacao de Emprestimo Via Post`() {
        val dataNascimento = LocalDate.now().minusYears(30).toString()
        val request = SimulacaoEmprestimoRequest(
            valorEmprestimo = BigDecimal("10000.00"),
            quantidadeParcelas = 12,
            dataNascimento = dataNascimento.let { LocalDate.parse(it) }
        )

        val headers = org.springframework.http.HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val httpEntity = HttpEntity(request, headers)

        val response = restTemplate.exchange(
            "http://localhost:$port/api/v1/simulacao-credito/simulacao",
            HttpMethod.POST,
            httpEntity,
            SimulacaoEmprestimoResult::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)

        val body = response.body!!
        assertNotNull(body.valorParcelaMensal)
        assertNotNull(body.totalJurosPago)
        assertNotNull(body.valorEmprestimo)
        assertTrue(body.valorEmprestimo > request.valorEmprestimo)
    }

    @Test
    fun `Simulação de consulta de juros GET`() {
        val dataNascimento = LocalDate.now().minusYears(50).toString()
        val response = restTemplate.getForEntity(
            "http://localhost:$port/api/v1/simulacao-credito/avaliacao?dataNascimento=$dataNascimento",
            Map::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body!!
        assertEquals(dataNascimento, body["dataNascimento"])
        assertEquals(0.02, (body["juros"] as Number).toDouble()) // 50 anos => 2%
    }
}