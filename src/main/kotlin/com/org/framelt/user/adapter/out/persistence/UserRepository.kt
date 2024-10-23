package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.portfolio.adapter.out.PortfolioJpaRepository
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
    private val portfolioJpaRepository: PortfolioJpaRepository,
) : UserQueryPort,
    UserCommandPort {
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
        val userEntities = userJpaRepository.findAllByIdIn(ids)
        return userEntities.map { it.toDomain() }
    }

    override fun readByUsername(username: String): User? {
        val userEntity = userJpaRepository.findByNickname(username)
        return userEntity?.toDomain() ?: throw IllegalArgumentException("일치하는 사용자가 없습니다.")
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
        portfolioJpaRepository.deleteAllByManage(userJpaEntity)
        quitUserJpaRepository.save(QuitUserJpaEntity.fromDomain(userJpaEntity, quitReason))
    }
}
