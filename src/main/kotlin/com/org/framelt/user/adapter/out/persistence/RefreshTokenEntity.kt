package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.user.application.port.out.persistence.RefreshToken
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "refresh_token")
class RefreshTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    val token: String,
    @Column(nullable = false)
    val isValid: Boolean = true,
) {
    fun toDomain() =
        RefreshToken(
            id = id,
            userId = userId,
            token = token,
            isValid = isValid,
        )

    companion object {
        fun fromDomain(refreshToken: RefreshToken) =
            RefreshTokenEntity(
                id = refreshToken.id,
                userId = refreshToken.userId,
                token = refreshToken.token,
                isValid = refreshToken.isValid,
            )
    }
}
