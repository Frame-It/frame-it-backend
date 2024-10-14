package com.org.framelt.portfolio.adapter.out

import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PortfolioJpaRepository : JpaRepository<PortfolioJpaEntity, Long> {
    fun findAllByManageId(
        userId: Long,
        pageable: Pageable,
    ): Page<PortfolioJpaEntity>

    fun findAllByManageId(userId: Long): List<PortfolioJpaEntity>

    @Query("SELECT p FROM PortfolioJpaEntity p WHERE p.manage.identity = 'PHOTOGRAPHER'")
    fun findAllByPhotographer(pageable: Pageable): Page<PortfolioJpaEntity>

    @Query("SELECT p FROM PortfolioJpaEntity p WHERE p.manage.identity = 'MODEL'")
    fun findAllByModel(pageable: Pageable): Page<PortfolioJpaEntity>

    fun deleteAllByManage(manage: UserJpaEntity)
}
