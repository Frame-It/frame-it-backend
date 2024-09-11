package com.org.framelt.user.adapter.out.oauth

import com.org.framelt.user.application.port.out.oauth.AuthPort
import org.springframework.stereotype.Component

@Component
class OAuthAdapter(
    private val oAuthClients: OAuthClients,
) : AuthPort {
    override fun getProviderUserId(
        provider: String,
        code: String,
    ): String {
        val oAuthProvider = OAuthProvider.of(provider)
        val oAuthProviderUserId = oAuthClients.getOAuthProviderUserId(oAuthProvider, code)
        return oAuthProviderUserId
    }
}
