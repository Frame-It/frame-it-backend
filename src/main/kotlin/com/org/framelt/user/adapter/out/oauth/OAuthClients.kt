package com.org.framelt.user.adapter.out.oauth

import com.org.framelt.user.application.port.out.oauth.AuthProfile
import java.util.EnumMap

class OAuthClients(
    private val clients: Set<OAuthClient>,
) {
    private val clientsByProvider: Map<OAuthProvider, OAuthClient>

    init {
        val clientsMap = EnumMap<OAuthProvider, OAuthClient>(OAuthProvider::class.java)
        clients.forEach { client ->
            clientsMap[client.getOAuthProvider()] = client
        }
        this.clientsByProvider = clientsMap
    }

    fun getProfile(
        oAuthProvider: OAuthProvider,
        code: String,
        redirectUri: String,
    ): AuthProfile {
        val oAuthClient = clientsByProvider[oAuthProvider] ?: throw IllegalArgumentException("지원되지 않는 OAuthProvider 입니다.")
        val oAuthProfileResponse = oAuthClient.getProfile(code, redirectUri)
        return AuthProfile(
            providerUserId = oAuthProfileResponse.providerUserId,
            email = oAuthProfileResponse.email,
        )
    }
}
