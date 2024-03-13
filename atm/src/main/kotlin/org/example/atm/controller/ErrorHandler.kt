package org.example.atm.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException

@ControllerAdvice
class ErrorHandler {
    @ExceptionHandler(HttpClientErrorException::class)
    fun handleHttpClientErrorException(ex: HttpClientErrorException): ResponseEntity<ErrorBody> {
        val err = ErrorBody(ex.message)
        return ResponseEntity.status(ex.statusCode).body(err)
    }


    data class ErrorBody(val message: String?)

}
