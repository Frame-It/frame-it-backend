package com.org.framelt.user.application.port.out.oauth

data class AuthProfile(
    val providerUserId: String,
    val email: String,
)
