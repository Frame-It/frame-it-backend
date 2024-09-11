package com.org.framelt.user.adapter.out.oauth

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

    fun getOAuthProviderUserId(
        oAuthProvider: OAuthProvider,
        code: String,
    ): String {
        val oAuthClient = clientsByProvider[oAuthProvider] ?: throw IllegalArgumentException("지원되지 않는 OAuthProvider 입니다.")
        return oAuthClient.getProviderUserId(code)
    }
}
