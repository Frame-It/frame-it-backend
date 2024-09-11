package com.org.framelt.user.application.service

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginUseCase
import com.org.framelt.user.application.port.out.oauth.AuthPort
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authPort: AuthPort,
    val userQueryPort: UserQueryPort,
    val userCommandPort: UserCommandPort,
) : LoginUseCase {
    override fun login(loginCommand: LoginCommand): Long {
        val providerUserId = authPort.getProviderUserId(loginCommand.provider, loginCommand.code)
        val provider = OAuthProvider.of(loginCommand.provider)
        val user =
            userQueryPort.findByProviderAndProviderUserId(provider, providerUserId)
                ?: signUp(provider, providerUserId)
        return user.id!!
    }

    private fun signUp(
        provider: OAuthProvider,
        providerUserId: String,
    ): User =
        userCommandPort.save(
            user = User.beforeCompleteSignUp(),
            provider = provider,
            providerUserId = providerUserId,
        )
}
