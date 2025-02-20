package com.org.framelt.portfolio.adapter.`in`

import com.org.framelt.portfolio.application.service.PortfolioDetailResponse
import com.org.framelt.portfolio.application.service.PortfolioResponse
import com.org.framelt.portfolio.domain.Portfolio
import org.springframework.data.domain.Page

class PortfolioMapper {
    companion object {
        fun toResponse(readAllPortfolio: Page<Portfolio>): Page<PortfolioResponse> =
            readAllPortfolio.map { portfolio ->
                PortfolioResponse(
                    id = portfolio.getId(),
                    title = portfolio.title,
                    userId = portfolio.manage.id!!,
                    identity = portfolio.manage.identity.toString(),
                    profileImageUrl = portfolio.manage.profileImageUrl ?: null,
                    portfolioImageUrl = portfolio.primaryPhoto,
                    userName = portfolio.manage.name,
                )
            }

        fun toDetailResponse(readPortfolio: Portfolio): PortfolioDetailResponse =
            PortfolioDetailResponse(
                id = readPortfolio.getId(),
                title = readPortfolio.title,
                userId = readPortfolio.manage.id!!,
                userName = readPortfolio.manage.name,
                userNickname = readPortfolio.manage.nickname,
                identity = readPortfolio.manage.identity.toString(),
                profileImageUrl = readPortfolio.manage.profileImageUrl,
                description = readPortfolio.description,
                photosUrl = readPortfolio.photos,
                hashtags = readPortfolio.hashtags,
                collaborators = readPortfolio.collaborator?.name,
                viewCount = readPortfolio.viewCount,
                createdAt = readPortfolio.createAt.toString(),
            )
    }
}
