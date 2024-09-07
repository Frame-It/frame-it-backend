package com.org.framelt.portfolio.adapter.out

import com.org.framelt.portfolio.application.port.out.PortfolioCommendPort
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.portfolio.domain.Portfolio
import com.org.framelt.user.adapter.out.UserJpaEntity
import org.springframework.stereotype.Repository

@Repository
class PortfolioRepository(
    private val portfolioJpaRepository: PortfolioJpaRepository
) : PortfolioCommendPort, PortfolioReadPort {

    override fun create(portfolio: Portfolio): Portfolio {
        val portfolioEntity = toEntity(portfolio)
        val savedEntity = portfolioJpaRepository.save(portfolioEntity)
        return toDomain(savedEntity)
    }

    override fun update(portfolio: Portfolio) {
        val portfolioEntity = toEntity(portfolio)
        portfolioJpaRepository.save(portfolioEntity)
    }

    override fun delete(portfolioId: Long) {
        portfolioJpaRepository.deleteById(portfolioId)
    }

    override fun readById(portfolioId: Long): Portfolio {
        val portfolioEntity = portfolioJpaRepository.findById(portfolioId)
            .orElseThrow { RuntimeException("Portfolio not found") }
        return toDomain(portfolioEntity)
    }

    override fun readByUserId(userId: Long): List<Portfolio> {
        val portfolioEntities = portfolioJpaRepository.findAllByManageId(userId)
        return portfolioEntities.map { toDomain(it) }
    }

    private fun toEntity(portfolio: Portfolio): PortfolioJpaEntity {
        return PortfolioJpaEntity(
            id = portfolio.id,
            manage = UserJpaEntity(
                id = portfolio.manage.id,
                name = portfolio.manage.name,
                nickname = portfolio.manage.nickname,
                profileImageUrl = portfolio.manage.profileImageUrl,
                bio = portfolio.manage.bio,
                identity = portfolio.manage.identity,
                career = portfolio.manage.career,
                shootingConcepts = portfolio.manage.shootingConcepts.map { it.name },
                notificationsEnabled = portfolio.manage.notificationsEnabled,
                deviceToken = portfolio.manage.deviseToken
            ),
            title = portfolio.title,
            description = portfolio.description,
            primaryPhoto = portfolio.primaryPhoto,
            photos = portfolio.photos,
            hashtags = portfolio.hashtags,
            collaborators = portfolio.collaborators.map { UserJpaEntity(it.id, it.name, it.nickname, it.profileImageUrl, it.bio, it.identity, it.career, it.shootingConcepts.map { concept -> concept.name }, it.notificationsEnabled, it.deviseToken) }
        )
    }

    private fun toDomain(portfolioEntity: PortfolioJpaEntity): Portfolio {
        return Portfolio(
            id = portfolioEntity.id,
            manage = portfolioEntity.manage.toDomain(),
            title = portfolioEntity.title,
            description = portfolioEntity.description,
            primaryPhoto = portfolioEntity.primaryPhoto,
            photos = portfolioEntity.photos,
            hashtags = portfolioEntity.hashtags,
            collaborators = portfolioEntity.collaborators.map { it.toDomain() }
        )
    }
}
