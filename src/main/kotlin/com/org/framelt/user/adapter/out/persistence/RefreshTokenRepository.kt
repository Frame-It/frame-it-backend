package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.user.application.port.out.persistence.RefreshToken
import com.org.framelt.user.application.port.out.persistence.RefreshTokenCommandPort
import com.org.framelt.user.application.port.out.persistence.RefreshTokenQueryPort
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepository(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
) : RefreshTokenCommandPort, RefreshTokenQueryPort {
    override fun save(refreshToken: RefreshToken) {
        refreshTokenJpaRepository.save(RefreshTokenEntity.fromDomain(refreshToken))
    }

    override fun findByToken(token: String): RefreshToken? {
        return refreshTokenJpaRepository.findByToken(token)?.toDomain()
    }

    override fun deleteAllByUserId(userId: Long) {
        refreshTokenJpaRepository.deleteAllByUserId(userId)
    }

    override fun findAllByUserId(userId: Long): List<RefreshToken> {
        return refreshTokenJpaRepository.findAllByUserId(userId).map { it.toDomain() }
    }
}
