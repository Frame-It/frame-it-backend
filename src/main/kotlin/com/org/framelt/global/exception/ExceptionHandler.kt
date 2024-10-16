package com.org.framelt.global.exception

import io.jsonwebtoken.ExpiredJwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler(
    private val internalServerErrorMessageConverter: InternalServerErrorMessageConverter,
) {
    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.warn("[IllegalArgumentException]", e)
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse(e.message))
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleExpiredJwtException(e: ExpiredJwtException): ResponseEntity<ErrorResponse> {
        logger.warn("[ExpiredJwtException]", e)
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse("토큰이 만료되었습니다."))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("[Exception]", e)
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponse(internalServerErrorMessageConverter.convert(e)))
    }
}
