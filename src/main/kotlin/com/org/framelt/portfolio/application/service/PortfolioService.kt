package com.org.framelt.portfolio.application.service

import com.org.framelt.portfolio.adapter.`in`.*
import com.org.framelt.portfolio.adapter.out.FileUploadClient
import com.org.framelt.portfolio.application.port.`in`.PortfolioCreateUseCase
import com.org.framelt.portfolio.application.port.out.PortfolioCommendPort
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.portfolio.domain.Portfolio
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.Identity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
        val togethers =
            command.portfoliosCreateRequest.togethers?.let { userQueryPort.readByIds(it) }
                ?: emptyList()
        val fileLinks =
            command.portfoliosCreateRequest.photos.map { photo ->
                fileUploadClient
                    .upload(photo.originalFilename!!, MediaType.IMAGE_JPEG, photo.bytes)
                    .orElseThrow { IllegalArgumentException("사진 업로드에 실패 했습니다. ${photo.name}") }
            }

        val portfolio =
            Portfolio(
                user,
                command.portfoliosCreateRequest.title,
                command.portfoliosCreateRequest.description,
                fileLinks,
                command.portfoliosCreateRequest.hashtags,
                togethers,
            )
        val savePortfolio = portfolioCommendPort.create(portfolio)
        return savePortfolio.getId()
    }

    override fun read(command: PortfolioReadCommend): PortfolioDetailResponse {
        val readPortfolio = portfolioReadPort.readById(command.id)
        return PortfolioMapper.toDetailResponse(readPortfolio)
    }

    override fun readAll(pageable: Pageable): Page<PortfolioResponse> {
        val readAllPortfolio = portfolioReadPort.readAll(pageable)
        return PortfolioMapper.toResponse(readAllPortfolio)
    }

    override fun readByUserId(
        command: PortfolioReadAllCommend,
        pageable: Pageable,
    ): Page<PortfolioResponse> {
        val readAllPortfolio = portfolioReadPort.readByUserId(command.userId, pageable)
        readAllPortfolio.filter { it.isOwnedByUser(command.userId) }
        return PortfolioMapper.toResponse(readAllPortfolio)
    }

    override fun readAllByMe(
        command: PortfolioReadAllCommend,
        pageable: Pageable,
    ): Page<PortfolioResponse> {
        val readAllPortfolio = portfolioReadPort.readByUserId(command.userId, pageable)
        readAllPortfolio.filter { it.isOwnedByUser(command.userId) }
        return PortfolioMapper.toResponse(readAllPortfolio)
    }

    override fun readAllByPhotographer(pageable: Pageable): Page<PortfolioResponse> {
        val readAllPortfolio = portfolioReadPort.readByPhotographer(pageable)
        readAllPortfolio.filter { it.isOwnedByIdentity(Identity.PHOTOGRAPHER) }
        return PortfolioMapper.toResponse(readAllPortfolio)
    }

    override fun readAllByModel(pageable: Pageable): Page<PortfolioResponse> {
        val readAllPortfolio = portfolioReadPort.readByModel(pageable)
        readAllPortfolio.filter { it.isOwnedByIdentity(Identity.MODEL) }
        return PortfolioMapper.toResponse(readAllPortfolio)
    }

    override fun update(command: PortfolioUpdateCommend) {
        val user = userQueryPort.readById(command.userId)
        val togethers = command.togethers?.let { userQueryPort.readByIds(it) }
        val findPortfolio = portfolioReadPort.readById(command.portfolioId)
        findPortfolio.deletePhotos(command.deletePhotos)
        val fileLinks =
            command.addPhotos?.map { photo ->
                fileUploadClient
                    .upload(photo.name, MediaType.IMAGE_JPEG, photo.bytes)
                    .orElseThrow { IllegalArgumentException("사진 업로드에 실패 했습니다. ${photo.name}") }
            }

        val updatePortfolio = findPortfolio.update(
            command.title,
            command.description,
            fileLinks,
            command.hashtags,
            togethers
        )
        portfolioCommendPort.update(updatePortfolio)
    }

    override fun delete(command: PortfolioDeleteCommend) {
        portfolioCommendPort.delete(command.id)
    }
}
