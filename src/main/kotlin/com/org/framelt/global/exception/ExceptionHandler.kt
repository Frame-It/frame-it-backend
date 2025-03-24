package com.org.framelt.global.exception

import io.jsonwebtoken.JwtException
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

    @ExceptionHandler(JwtException::class)
    fun handleJwtException(e: JwtException): ResponseEntity<ErrorResponse> {
        logger.warn("[JwtException]", e)
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse("JWT를 파싱하는 중 문제가 발생했습니다: ${e.message}."))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("[Exception]", e)
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponse(internalServerErrorMessageConverter.convert(e)))
    }
}
