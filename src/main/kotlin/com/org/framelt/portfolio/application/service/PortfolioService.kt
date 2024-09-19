package com.org.framelt.portfolio.application.service

import com.org.framelt.portfolio.adapter.`in`.*
import com.org.framelt.portfolio.adapter.out.FileUploadClient
import com.org.framelt.portfolio.application.port.`in`.PortfolioCreateUseCase
import com.org.framelt.portfolio.application.port.out.PortfolioCommendPort
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.portfolio.domain.Portfolio
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
class PortfolioService(
    private val portfolioCommendPort: PortfolioCommendPort,
    private val portfolioReadPort: PortfolioReadPort,
    private val userQueryPort: UserQueryPort,
    private val fileUploadClient: FileUploadClient,
) : PortfolioCreateUseCase {

    override fun create(command: PortfolioCreateCommend): Long {
        val user = userQueryPort.readById(command.userId)
        val togethers = userQueryPort.readByIds(command.togethers)
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
        userQueryPort.readById(command.userId)
        val readAllPortfolio = portfolioReadPort.readByUserId(command.targetId)
        readAllPortfolio.filter { it.isOwnedByUser(command.userId) }
        return PortfolioMapper.toResponse(readAllPortfolio)
    }

    override fun update(command: PortfolioUpdateCommend) {
        val user = userQueryPort.readById(command.userId)
        val togethers = userQueryPort.readByIds(command.togethers)
        val fileLinks = command.photos.map { photo ->
            fileUploadClient.upload(photo.name, MediaType.IMAGE_JPEG, photo.bytes)
                .orElseThrow { IllegalArgumentException("사진 업로드에 실패 했습니다. ${photo.name}") }
        }

        val portfolio = Portfolio(user, command.title, command.description, fileLinks, command.hashtags, togethers)
        portfolioCommendPort.update(portfolio)
    }

    override fun delete(command: PortfolioDeleteCommend) {
        val portfolio = portfolioReadPort.readById(command.id)
        if (portfolio.isOwnedByUser(command.userId)) throw IllegalArgumentException("해당 포트폴리오에 권한이 없는 사용자 입니다.")
        portfolioCommendPort.delete(command.id)
    }
}
