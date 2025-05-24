package com.credit.simulator.creditsimulationservice.exception

import com.credit.simulator.exception.BusinessException
import com.credit.simulator.exception.HttpMessageNotReadableException
import com.credit.simulator.exception.InvalidDateFormatException
import com.credit.simulator.exception.ParcelasExcedemLimiteException
import com.credit.simulator.exception.ValorMenorQuePermitido
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.time.format.DateTimeParseException
import org.springframework.core.MethodParameter

class GlobalExceptionHandlerTest {

    private lateinit var globalExceptionHandler: GlobalExceptionHandler

    @BeforeEach
    fun setUp() {
        globalExceptionHandler = GlobalExceptionHandler()
    }

    @Test
    fun `handleInvalidDateFormat retorna BAD_REQUEST com mensagem correta`() {
        val exceptionMessage = "Data de formato inválido: 2023/10/20"
        val exception = InvalidDateFormatException(exceptionMessage)
        val response = globalExceptionHandler.handleInvalidDateFormat(exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(mapOf("erro" to exceptionMessage), response.body)
    }

    @Test
    fun `handleParseException retorna BAD_REQUEST com mensagem especifica`() {
        val exception = DateTimeParseException("Text '2023-XX-15' could not be parsed", "2023-XX-15", 5)
        val response = globalExceptionHandler.handleParseException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(mapOf("erro" to "Formato de data inválido. Use o padrão yyyy-MM-dd."), response.body)
    }

        @Test
    fun `handleParcelasExcedemLimite retorna BAD_REQUEST com mensagem especifica`() {
        val exception = ParcelasExcedemLimiteException(message = "A quantidade máxima de parcelas permitida é 48.")
        val response = globalExceptionHandler.handleParcelasExcedemLimite(exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(mapOf("erro" to "A quantidade máxima de parcelas permitida é 48."), response.body)
    }

    @Test
    fun `handleValorMenorQuePermitido retorna BAD_REQUEST com mensagem especifica`() {
        val exception = ValorMenorQuePermitido(message = "valor menor que permitido")
        val response = globalExceptionHandler.handleValorMenorQuePermitido(exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(mapOf("erro" to "Valor menor que permitido"), response.body)
    }

    @Test
    fun `handleBusinessException retorna BAD_REQUEST com mensagem especifica`() {
        val exception = BusinessException("O campo não pode ser nulo ou vazio.")
        val response = globalExceptionHandler.handleBusinessException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(mapOf("erro" to "O campo não pode ser nulo ou vazio."), response.body)
    }

        @Test
    fun `handleHttpMessageNotReadableException retorna BAD_REQUEST com mensagem especifica`() {
        val exception = HttpMessageNotReadableException("JSON parse error: Unexpected end-of-input")
        val response = globalExceptionHandler.handleHttpMessageNotReadableException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(mapOf("erro" to "o campo não pode ser nulo ou vazio."), response.body)
    }
}