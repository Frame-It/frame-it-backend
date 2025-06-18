package com.org.framelt.user.adapter.out.persistence

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

interface RefreshTokenJpaRepository : Repository<RefreshTokenEntity, Long> {
    fun save(refreshTokenEntity: RefreshTokenEntity)

    fun findByToken(token: String): RefreshTokenEntity?

    fun deleteAllByUserId(userId: Long)

    fun findAllByUserId(userId: Long): List<RefreshTokenEntity>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM refresh_token r WHERE r.userId = :userId AND r.isValid = true")
    fun findValidOneByUserIdWithLock(@Param("userId") userId: Long): RefreshTokenEntity?
}
