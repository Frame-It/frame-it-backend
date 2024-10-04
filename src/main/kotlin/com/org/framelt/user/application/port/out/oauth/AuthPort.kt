package com.org.framelt.user.application.port.out.oauth

interface AuthPort {
    fun getProfile(
        provider: String,
        code: String,
        redirectUri: String,
    ): AuthProfile
}
