package com.credit.simulator.creditsimulationservice.exception

import com.credit.simulator.exception.BusinessException
import com.credit.simulator.exception.HttpMessageNotReadableException
import com.credit.simulator.exception.InvalidDateFormatException
import com.credit.simulator.exception.ParcelasExcedemLimiteException
import com.credit.simulator.exception.ValorMenorQuePermitido
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.time.format.DateTimeParseException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateFormatException::class)
    fun handleInvalidDateFormat(ex: InvalidDateFormatException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "erro" to ex.message
            )
        )
    }

    @ExceptionHandler(DateTimeParseException::class)
    fun handleParseException(ex: DateTimeParseException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "erro" to "Formato de data inválido. Use o padrão yyyy-MM-dd."
            )
        )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleArgumentMismatch(ex: MethodArgumentTypeMismatchException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "erro" to "Parâmetro inválido: ${ex.name}"
            )
        )
    }

    @ExceptionHandler(ParcelasExcedemLimiteException::class)
    fun handleParcelasExcedemLimite(ex: ParcelasExcedemLimiteException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "erro" to "A quantidade máxima de parcelas permitida é 48."
            )
        )
    }

    @ExceptionHandler(ValorMenorQuePermitido::class)
    fun handleValorMenorQuePermitido(ex: ValorMenorQuePermitido): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "erro" to "Valor menor que permitido"
            )
        )
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "erro" to "O campo não pode ser nulo ou vazio."
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf(
                "erro" to "o campo não pode ser nulo ou vazio."
            )
        )
    }
}