package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.application.port.out.persistence.OAuthUserCommandPort
import com.org.framelt.user.application.port.out.persistence.OAuthUserModel
import org.springframework.stereotype.Repository

@Repository
class OAuthUserRepository(
    private val oAuthUserJpaRepository: OAuthUserJpaRepository,
) : OAuthUserQueryPort,
    OAuthUserCommandPort {
    override fun findByProviderAndProviderUserId(
        provider: OAuthProvider,
        providerUserId: String,
    ): OAuthUserModel? {
        val oAuthUserJpaEntity = oAuthUserJpaRepository.findByProviderAndProviderUserId(provider, providerUserId)
        return oAuthUserJpaEntity?.toModel()
    }

    override fun save(oAuthUserModel: OAuthUserModel): OAuthUserModel {
        val oAuthUserJpaEntity = OAuthUserJpaEntity.fromModel(oAuthUserModel)
        val savedOAuthUserJpaEntity = oAuthUserJpaRepository.save(oAuthUserJpaEntity)
        return savedOAuthUserJpaEntity.toModel()
    }

    override fun readById(id: Long): OAuthUserModel {
        val oAuthUserJpaEntity =
            oAuthUserJpaRepository.findById(id)
                ?: throw IllegalArgumentException("OAuth 회원이 존재하지 않습니다: $id")
        return oAuthUserJpaEntity.toModel()
    }
}
