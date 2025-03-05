package com.org.framelt.user.adapter.out.persistence

import org.springframework.data.repository.Repository

interface RefreshTokenJpaRepository : Repository<RefreshTokenEntity, Long> {
    fun save(refreshTokenEntity: RefreshTokenEntity)

    fun findByToken(token: String): RefreshTokenEntity?

    fun deleteAllByUserId(userId: Long)

    fun findAllByUserId(userId: Long): List<RefreshTokenEntity>
}
