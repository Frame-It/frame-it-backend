package com.org.framelt.portfolio.application.service

import com.org.framelt.portfolio.adapter.`in`.*
import com.org.framelt.portfolio.application.port.`in`.PortfolioCreateUseCase
import com.org.framelt.portfolio.application.port.out.PortfolioCommendPort
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.portfolio.domain.Portfolio
import com.org.framelt.user.application.port.out.UserReadPort
import org.springframework.stereotype.Service

@Service
class PortfolioService(
    private val portfolioCommendPort: PortfolioCommendPort,
    private val portfolioReadPort: PortfolioReadPort,
    private val userReadPort: UserReadPort,
) : PortfolioCreateUseCase {

    override fun create(command: PortfolioCreateCommend): Long {
        val user = userReadPort.readById(command.userId)
        val portfolio = Portfolio(user, command.title, command.description, command.photos, command.togethers)
        val savePortfolio = portfolioCommendPort.create(portfolio)
        return savePortfolio.getId()
    }

    override fun read(command: PortfolioReadCommend): PortfolioDetailResponse {
        val readPortfolio = portfolioReadPort.readById(command.id)
        readPortfolio.isOwnedByUser(command.userId)
        return PortfolioMapper.toDetailResponse(readPortfolio)
    }

    override fun readAll(command: PortfolioReadAllCommend): List<PortfolioResponse> {
        val readAllPortfolio = portfolioReadPort.readByUserId(command.userId)
        readAllPortfolio.filter { it.isOwnedByUser(command.userId) }
        return PortfolioMapper.toResponse(readAllPortfolio)
    }

    override fun update(command: PortfolioUpdateCommend): Void {
        val user = userReadPort.readById(command.userId)
        val portfolio = Portfolio(user, command.title, command.description, command.photos, command.togethers)
        portfolioCommendPort.update(portfolio)
    }

    override fun delete(command: PortfolioDeleteCommend): Void {
        portfolioCommendPort.delete(command.id)
    }
}
