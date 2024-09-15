package com.org.framelt.portfolio.adapter.`in`

import com.org.framelt.portfolio.application.service.PortfolioDetailResponse
import com.org.framelt.portfolio.application.service.PortfolioResponse
import com.org.framelt.portfolio.domain.Portfolio

class PortfolioMapper {

    companion object {
        fun toCreate(userId: Long, request: PortfolioCreateRequest): PortfolioCreateCommend {
            return PortfolioCreateCommend(
                userId,
                photos = request.images,
                title = request.title,
                description = request.description,
                hashtags = request.hashTags,
                togethers = request.togethers
            )
        }

        fun toResponse(readAllPortfolio: List<Portfolio>): List<PortfolioResponse> {
            return readAllPortfolio.map { portfolio ->
                PortfolioResponse(
                    id = portfolio.getId(),
                    title = portfolio.title,
                    userId = portfolio.manage.id,
                    userName = portfolio.manage.name
                )
            }
        }

        fun toDetailResponse(readPortfolio: Portfolio): PortfolioDetailResponse {
            return PortfolioDetailResponse(
                id = readPortfolio.getId(),
                title = readPortfolio.title,
                userId = readPortfolio.manage.id,
                userName = readPortfolio.manage.name,
                description = readPortfolio.description,
                photosUrl = readPortfolio.photos.map { it.originalFilename ?: "" },
                hashtags = readPortfolio.hashtags,
                collaborators = readPortfolio.collaborators.joinToString(", ") { it.name }
            )
        }
    }
}