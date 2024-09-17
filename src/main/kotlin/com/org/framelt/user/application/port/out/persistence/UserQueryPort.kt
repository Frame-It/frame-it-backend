package com.org.framelt.user.application.port.out.persistence

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.domain.User

interface UserQueryPort {
    fun readById(id: Long): User

    fun findById(id: Long): User?

    fun findByProviderAndProviderUserId(
        provider: OAuthProvider,
        providerUserId: String,
    ): User?

    fun existsByNickname(nickname: String): Boolean
}
