package com.org.framelt.user.application.service

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.adapter.out.persistence.OAuthUserQueryPort
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginResult
import com.org.framelt.user.application.port.`in`.LoginUseCase
import com.org.framelt.user.application.port.out.JwtPort
import com.org.framelt.user.application.port.out.oauth.AuthPort
import com.org.framelt.user.application.port.out.persistence.OAuthUserCommandPort
import com.org.framelt.user.application.port.out.persistence.OAuthUserModel
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.Identity
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authPort: AuthPort,
    val jwtPort: JwtPort,
    val userQueryPort: UserQueryPort,
    val oauthUserQueryPort: OAuthUserQueryPort,
    val oauthUserCommandPort: OAuthUserCommandPort,
) : LoginUseCase {
    override fun login(loginCommand: LoginCommand): LoginResult {
        val authProfile = authPort.getProfile(loginCommand.provider, loginCommand.code, loginCommand.redirectUri)
        val provider = OAuthProvider.of(loginCommand.provider)
        val oauthUser =
            oauthUserQueryPort.findByProviderAndProviderUserId(provider, authProfile.providerUserId)
                ?: oauthUserCommandPort.save(
                    OAuthUserModel(
                        provider = provider,
                        providerUserId = authProfile.providerUserId,
                        email = authProfile.email,
                    ),
                )
        val user = oauthUser.user

        return LoginResult(
            accessToken = jwtPort.createToken(user?.id.toString()),
            signUpCompleted = user != null,
            oauthUserId = oauthUser.id!!,
            identity = user?.identity ?: Identity.NONE,
        )
    }
}
