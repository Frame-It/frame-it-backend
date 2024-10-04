package com.org.framelt.user.adapter.out.oauth

interface OAuthClient {
    fun getProfile(
        code: String,
        redirectUri: String,
    ): OAuthProfileResponse

    fun getOAuthProvider(): OAuthProvider
}
