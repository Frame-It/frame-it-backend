package com.org.framelt.portfolio.application.port.`in`

import com.org.framelt.portfolio.adapter.`in`.*
import com.org.framelt.portfolio.application.service.PortfolioDetailResponse
import com.org.framelt.portfolio.application.service.PortfolioResponse

interface PortfolioCreateUseCase {
    fun create(command: PortfolioCreateCommend): Long
    fun read(command: PortfolioReadCommend): PortfolioDetailResponse
    fun readAll(command: PortfolioReadAllCommend): List<PortfolioResponse>
    fun update(command: PortfolioUpdateCommend)
    fun  delete(command: PortfolioDeleteCommend)
}
