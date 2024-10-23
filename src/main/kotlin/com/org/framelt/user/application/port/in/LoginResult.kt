package com.org.framelt.user.application.port.`in`

import com.org.framelt.user.domain.Identity

data class LoginResult(
    val accessToken: String?,
    val signUpCompleted: Boolean,
    val oauthUserId: Long,
    val identity: Identity,
)
