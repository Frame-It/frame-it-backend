package com.org.framelt.portfolio.adapter.`in`

import com.org.framelt.portfolio.application.port.`in`.PortfolioCreateUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController("/portfolio")
class PortfolioController(
    private val portfolioCreateUseCase: PortfolioCreateUseCase,
) {
    fun create(@AuthenticationPrincipal userId: Long, @RequestBody request: PortfolioCreateRequest): ResponseEntity<Long> {
        val createCommend = PortfolioMapper.toCreate(userId, request)
        val userId = portfolioCreateUseCase.create(createCommend)
        return ResponseEntity.ok(userId)
    }
}