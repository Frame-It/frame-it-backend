package com.org.framelt.portfolio.application.port.`in`

import com.org.framelt.portfolio.adapter.`in`.*
import com.org.framelt.portfolio.application.service.PortfolioDetailResponse
import com.org.framelt.portfolio.application.service.PortfolioResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PortfolioCreateUseCase {
    fun create(command: PortfolioCreateCommend): Long
    fun read(command: PortfolioReadCommend): PortfolioDetailResponse
    fun update(command: PortfolioUpdateCommend)
    fun delete(command: PortfolioDeleteCommend)
    fun readAllByPhotographer(pageable: Pageable): Page<PortfolioResponse>
    fun readAllByModel(pageable: Pageable): Page<PortfolioResponse>
    fun readAllByMe(command: PortfolioReadAllCommend, pageable: Pageable): Page<PortfolioResponse>
    fun readAll(pageable: Pageable): Page<PortfolioResponse>
    fun readByUserId(command: PortfolioReadAllCommend, pageable: Pageable): Page<PortfolioResponse>
}
