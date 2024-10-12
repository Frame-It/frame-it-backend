package com.org.framelt.portfolio.adapter.out

import com.org.framelt.portfolio.application.port.out.PortfolioCommendPort
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.portfolio.domain.Portfolio
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PortfolioRepository(
    private val portfolioJpaRepository: PortfolioJpaRepository,
) : PortfolioCommendPort,
    PortfolioReadPort {
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
        val portfolioEntity =
            portfolioJpaRepository
                .findById(portfolioId)
                .orElseThrow { RuntimeException("Portfolio not found") }
        return toDomain(portfolioEntity)
    }

    override fun readAll(pageable: Pageable): Page<Portfolio> = portfolioJpaRepository.findAll(pageable).map { toDomain(it) }

    override fun readByPhotographer(pageable: Pageable): Page<Portfolio> =
        portfolioJpaRepository.findAllByPhotographer(pageable).map {
            toDomain(it)
        }

    override fun readByModel(pageable: Pageable): Page<Portfolio> = portfolioJpaRepository.findAllByModel(pageable).map { toDomain(it) }

    override fun readByUserId(
        userId: Long,
        pageable: Pageable,
    ): Page<Portfolio> {
        val portfolioEntities = portfolioJpaRepository.findAllByManageId(userId, pageable)
        return portfolioEntities.map { toDomain(it) }
    }

    override fun readByUserId(userId: Long): List<Portfolio> {
        val portfolioEntities = portfolioJpaRepository.findAllByManageId(userId)
        return portfolioEntities.map { toDomain(it) }
    }

    private fun toEntity(portfolio: Portfolio): PortfolioJpaEntity =
        PortfolioJpaEntity(
            id = portfolio.id,
            manage =
                UserJpaEntity(
                    id = portfolio.manage.id,
                    name = portfolio.manage.name,
                    nickname = portfolio.manage.nickname,
                    birthDate = portfolio.manage.birthDate,
                    isQuit = portfolio.manage.isQuit,
                    profileImageUrl = portfolio.manage.profileImageUrl,
                    bio = portfolio.manage.bio,
                    identity = portfolio.manage.identity,
                    career = portfolio.manage.career,
                    shootingConcepts = portfolio.manage.shootingConcepts,
                    notificationsEnabled = portfolio.manage.notificationsEnabled,
                    email = portfolio.manage.email,
                    deviseToken = portfolio.manage.deviseToken,
                ),
            title = portfolio.title,
            description = portfolio.description,
            primaryPhoto = portfolio.primaryPhoto,
            photos = portfolio.photos,
            hashtags = portfolio.hashtags,
            collaborators =
                portfolio.collaborators.map {
                    UserJpaEntity(
                        id = it.id,
                        name = it.name,
                        nickname = it.nickname,
                        birthDate = it.birthDate,
                        isQuit = it.isQuit,
                        profileImageUrl = it.profileImageUrl,
                        bio = it.bio,
                        identity = it.identity,
                        career = it.career,
                        shootingConcepts = it.shootingConcepts,
                        notificationsEnabled = it.notificationsEnabled,
                        email = it.email,
                        deviseToken = it.deviseToken,
                    )
                },
            createAt = portfolio.createAt
        )

    private fun toDomain(portfolioEntity: PortfolioJpaEntity): Portfolio =
        Portfolio(
            id = portfolioEntity.id,
            manage = portfolioEntity.manage.toDomain(),
            title = portfolioEntity.title,
            description = portfolioEntity.description,
            primaryPhoto = portfolioEntity.primaryPhoto,
            photos = portfolioEntity.photos,
            hashtags = portfolioEntity.hashtags,
            collaborators = portfolioEntity.collaborators.map { it.toDomain() },
            createAt = portfolioEntity.createAt
        )
}
