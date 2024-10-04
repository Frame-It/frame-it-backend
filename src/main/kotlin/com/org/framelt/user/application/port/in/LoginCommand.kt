package com.org.framelt.user.application.port.`in`

data class LoginCommand(
    val provider: String,
    val code: String,
    val redirectUri: String,
)
