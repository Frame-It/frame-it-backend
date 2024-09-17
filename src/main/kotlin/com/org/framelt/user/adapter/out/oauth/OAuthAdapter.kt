package com.org.framelt.user.adapter.out.oauth

import com.org.framelt.user.application.port.out.oauth.AuthPort
import com.org.framelt.user.application.port.out.oauth.AuthProfile
import org.springframework.stereotype.Component

@Component
class OAuthAdapter(
    private val oAuthClients: OAuthClients,
) : AuthPort {
    override fun getProfile(
        provider: String,
        code: String,
    ): AuthProfile {
        val oAuthProvider = OAuthProvider.of(provider)
        val oAuthProviderUserId = oAuthClients.getProfile(oAuthProvider, code)
        return oAuthProviderUserId
    }
}
