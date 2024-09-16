package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userJpaRepository: UserJpaRepository,
    private val oAuthUserJpaRepository: OAuthUserJpaRepository,
) : UserQueryPort,
    UserCommandPort {
    override fun save(
        user: User,
        provider: OAuthProvider,
        providerUserId: String,
    ): User {
        val userEntity = userJpaRepository.save(UserJpaEntity.fromDomain(user))
        oAuthUserJpaRepository.save(
            OAuthUserJpaEntity(
                provider = provider,
                providerUserId = providerUserId,
                user = userEntity,
            ),
        )
        return userEntity.toDomain()
    }

    override fun save(user: User): User {
        val userEntity = userJpaRepository.save(UserJpaEntity.fromDomain(user))
        return userEntity.toDomain()
    }

    override fun readById(id: Long): User {
        val userEntity = userJpaRepository.getById(id)
        return userEntity.toDomain()
    }

    override fun findById(id: Long): User? {
        val userEntity = userJpaRepository.findById(id)
        return userEntity?.toDomain()
    }

    override fun findByProviderAndProviderUserId(
        provider: OAuthProvider,
        providerUserId: String,
    ): User? {
        val oAuthUserJpaEntity =
            oAuthUserJpaRepository.findByProviderAndProviderUserId(
                provider,
                providerUserId,
            )
        return oAuthUserJpaEntity?.user?.toDomain()
    }

    override fun existsByNickname(nickname: String): Boolean = userJpaRepository.existsByNickname(nickname)
}
