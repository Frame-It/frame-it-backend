package com.org.framelt.user.application.port.`in`

import com.org.framelt.user.domain.Identity

data class SignUpResult(
    val accessToken: String,
    val identity: Identity,
)
