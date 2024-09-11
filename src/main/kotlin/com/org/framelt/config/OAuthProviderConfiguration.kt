package com.org.framelt.config

import com.org.framelt.user.adapter.out.oauth.OAuthClient
import com.org.framelt.user.adapter.out.oauth.OAuthClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OAuthProviderConfiguration {
    @Bean
    fun oauthClients(clients: Set<OAuthClient>): OAuthClients = OAuthClients(clients)
}
