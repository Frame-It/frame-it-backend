package com.org.framelt.portfolio.adapter.out

import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioJpaRepository : JpaRepository<PortfolioJpaEntity, Long> {
    fun findAllByManageId(userId: Long): List<PortfolioJpaEntity>
}
