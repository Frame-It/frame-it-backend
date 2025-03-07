package com.org.framelt.user.application.port.out.persistence

interface RefreshTokenCommandPort {
    fun save(refreshToken: RefreshToken)

    fun deleteAllByUserId(userId: Long)
}
