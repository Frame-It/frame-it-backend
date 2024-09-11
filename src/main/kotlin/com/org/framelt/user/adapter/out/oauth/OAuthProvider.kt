package com.org.framelt.user.adapter.out.oauth

enum class OAuthProvider {
    KAKAO,
    GOOGLE,
    ;

    companion object {
        fun of(provider: String): OAuthProvider = valueOf(provider.uppercase())
    }
}
