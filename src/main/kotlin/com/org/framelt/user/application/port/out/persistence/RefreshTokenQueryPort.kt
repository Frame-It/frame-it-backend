package com.org.framelt.user.application.port.out.persistence

interface RefreshTokenQueryPort {
    fun findByToken(token: String): RefreshToken?

    fun findValidOneByUserIdWithLock(userId: Long): RefreshToken?
}
