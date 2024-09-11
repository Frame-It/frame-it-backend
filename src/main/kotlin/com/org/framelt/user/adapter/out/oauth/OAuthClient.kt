package com.org.framelt.user.adapter.out.oauth

interface OAuthClient {
    fun getProviderUserId(code: String): String

    fun getOAuthProvider(): OAuthProvider
}
