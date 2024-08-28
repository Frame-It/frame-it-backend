package com.org.framelt.portfolio.adapter.`in`

import com.org.framelt.portfolio.application.port.`in`.PortfolioCreateUseCase
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController("/portfolio")
class PortfolioController(
    private val portfolioCreateUseCase: PortfolioCreateUseCase,
) {
    fun create(@AuthenticationPrincipal userId: Long, @RequestBody request: PortfolioCreateRequest): Long {
        val createCommend = PortfolioMapper.toCreate(userId, request)
        return portfolioCreateUseCase.create(createCommend)
    }
}