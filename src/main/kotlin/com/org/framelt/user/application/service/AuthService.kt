package com.org.framelt.user.application.service

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginUseCase
import com.org.framelt.user.application.port.out.JwtPort
import com.org.framelt.user.application.port.out.oauth.AuthPort
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authPort: AuthPort,
    val jwtPort: JwtPort,
    val userQueryPort: UserQueryPort,
    val userCommandPort: UserCommandPort,
) : LoginUseCase {
    override fun login(loginCommand: LoginCommand): String {
        val providerUserId = authPort.getProviderUserId(loginCommand.provider, loginCommand.code)
        val provider = OAuthProvider.of(loginCommand.provider)
        val user =
            userQueryPort.findByProviderAndProviderUserId(provider, providerUserId)
                ?: signUp(provider, providerUserId)
        return jwtPort.createToken(user.id!!.toString())
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
