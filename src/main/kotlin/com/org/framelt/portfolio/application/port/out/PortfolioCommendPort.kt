package com.org.framelt.portfolio.application.port.out

import com.org.framelt.portfolio.domain.Portfolio

interface PortfolioCommendPort {
    fun create(portfolio: Portfolio): Portfolio
    fun update(portfolio: Portfolio)
    fun delete(portfolioId: Long)
}
