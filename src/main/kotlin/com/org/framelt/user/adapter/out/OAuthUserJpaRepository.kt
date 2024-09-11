package com.org.framelt.user.adapter.out

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import org.springframework.data.repository.Repository

interface OAuthUserJpaRepository : Repository<OAuthUserJpaEntity, Long> {
    fun findByProviderAndProviderUserId(
        provider: OAuthProvider,
        providerUserId: String,
    ): OAuthUserJpaEntity?

    fun save(oAuthUserJpaEntity: OAuthUserJpaEntity): OAuthUserJpaEntity
}