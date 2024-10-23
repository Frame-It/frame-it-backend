package com.org.framelt.user.application.port.out.persistence

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.domain.User

data class OAuthUserModel(
    val id: Long? = null,
    val user: User? = null,
    val provider: OAuthProvider,
    val providerUserId: String,
    val email: String,
)
