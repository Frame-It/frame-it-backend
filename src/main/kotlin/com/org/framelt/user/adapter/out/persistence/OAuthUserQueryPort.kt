package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.application.port.out.persistence.OAuthUserModel

interface OAuthUserQueryPort {
    fun findByProviderAndProviderUserId(
        provider: OAuthProvider,
        providerUserId: String,
    ): OAuthUserModel?

    fun readById(id: Long): OAuthUserModel
}
