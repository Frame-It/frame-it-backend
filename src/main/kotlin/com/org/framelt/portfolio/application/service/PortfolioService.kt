package com.org.framelt.portfolio.application.service

import com.org.framelt.portfolio.adapter.`in`.*
import com.org.framelt.portfolio.adapter.out.FileUploadClient
import com.org.framelt.portfolio.application.port.`in`.PortfolioCreateUseCase
import com.org.framelt.portfolio.application.port.out.PortfolioCommendPort
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.portfolio.domain.Portfolio
import com.org.framelt.user.application.port.out.UserReadPort
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
class PortfolioService(
    private val portfolioCommendPort: PortfolioCommendPort,
    private val portfolioReadPort: PortfolioReadPort,
    private val userReadPort: UserReadPort,
    private val fileUploadClient: FileUploadClient
) : PortfolioCreateUseCase {

    override fun create(command: PortfolioCreateCommend): Long {
        val user = userReadPort.readById(command.userId)
        val togethers = userReadPort.readByIds(command.togethers)
        val fileLinks = command.photos.map { photo ->
            fileUploadClient.upload(photo.name, MediaType.IMAGE_JPEG, photo.bytes)
                .orElseThrow { IllegalArgumentException("사진 업로드에 실패 했습니다. ${photo.name}") }
        }

        val portfolio = Portfolio(user, command.title, command.description, fileLinks, command.hashtags, togethers)
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

    override fun update(command: PortfolioUpdateCommend) {
        val user = userReadPort.readById(command.userId)
        val togethers = userReadPort.readByIds(command.togethers)
        val fileLinks = command.photos.map { photo ->
            fileUploadClient.upload(photo.name, MediaType.IMAGE_JPEG, photo.bytes)
                .orElseThrow { IllegalArgumentException("사진 업로드에 실패 했습니다. ${photo.name}") }
        }

        val portfolio = Portfolio(user, command.title, command.description, fileLinks, command.hashtags, togethers)
        portfolioCommendPort.update(portfolio)
    }

    override fun delete(command: PortfolioDeleteCommend) {
        portfolioCommendPort.delete(command.id)
    }
}
