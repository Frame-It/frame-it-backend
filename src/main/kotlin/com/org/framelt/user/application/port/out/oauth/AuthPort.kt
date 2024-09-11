package com.org.framelt.user.application.port.out.oauth

interface AuthPort {
    fun getProviderUserId(
        provider: String,
        code: String,
    ): String
}
