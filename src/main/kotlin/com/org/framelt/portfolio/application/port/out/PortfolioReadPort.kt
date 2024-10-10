package com.org.framelt.portfolio.application.port.out

import com.org.framelt.portfolio.domain.Portfolio
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PortfolioReadPort {
    fun readById(portfolioId: Long): Portfolio
    fun readByUserId(userId: Long, pageable: Pageable): Page<Portfolio>
    fun readAll(pageable: Pageable): Page<Portfolio>
    fun readByPhotographer(pageable: Pageable): Page<Portfolio>
    fun readByModel(pageable: Pageable): Page<Portfolio>
}
