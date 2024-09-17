package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.project.adapter.out.ProjectJpaRepository
import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userJpaRepository: UserJpaRepository,
    private val oAuthUserJpaRepository: OAuthUserJpaRepository,
    private val quitUserJpaRepository: QuitUserJpaRepository,
    private val projectJpaRepository: ProjectJpaRepository,
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

    override fun readByIds(ids: List<Long>): List<User> {
        val userEntities = userJpaRepository.findAllByIds(ids)
        return userEntities.map { it.toDomain() }
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

    override fun quit(
        user: User,
        quitReason: String?,
    ) {
        val userJpaEntity = UserJpaEntity.fromDomain(user)
        userJpaRepository.save(userJpaEntity)
        oAuthUserJpaRepository.deleteByUser(userJpaEntity)
        projectJpaRepository.deleteAllByHost(userJpaEntity)
        // TODO: 포트폴리오 데이터 삭제 처리
        quitUserJpaRepository.save(QuitUserJpaEntity.fromDomain(userJpaEntity, quitReason))
    }
}
