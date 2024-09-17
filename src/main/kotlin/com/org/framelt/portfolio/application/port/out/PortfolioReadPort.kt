package com.org.framelt.portfolio.application.port.out

import com.org.framelt.portfolio.domain.Portfolio

interface PortfolioReadPort {
    fun readById(portfolioId: Long): Portfolio
    fun readByUserId(userId: Long): List<Portfolio>
}
