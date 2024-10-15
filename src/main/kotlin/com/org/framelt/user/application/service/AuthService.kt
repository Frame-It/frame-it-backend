package com.org.framelt.user.application.service

import com.org.framelt.notification.application.service.NotificationLetter
import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginResult
import com.org.framelt.user.application.port.`in`.LoginUseCase
import com.org.framelt.user.application.port.out.JwtPort
import com.org.framelt.user.application.port.out.oauth.AuthPort
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.User
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authPort: AuthPort,
    val jwtPort: JwtPort,
    val userQueryPort: UserQueryPort,
    val userCommandPort: UserCommandPort,
    val applicationEventPublisher: ApplicationEventPublisher,
) : LoginUseCase {
    override fun login(loginCommand: LoginCommand): LoginResult {
        val authProfile = authPort.getProfile(loginCommand.provider, loginCommand.code, loginCommand.redirectUri)
        val provider = OAuthProvider.of(loginCommand.provider)
        val user =
            userQueryPort.findByProviderAndProviderUserId(provider, authProfile.providerUserId)
                ?: signUp(authProfile.email, provider, authProfile.providerUserId)

        return LoginResult(
            accessToken = jwtPort.createToken(user.id!!.toString()),
            signUpCompleted = user.isSignUpCompleted(),
            identity = user.identity,
        )
    }

    private fun signUp(
        email: String,
        provider: OAuthProvider,
        providerUserId: String,
    ): User {
        val saveUser = userCommandPort.save(
            user = User.beforeCompleteSignUp(email),
            provider = provider,
            providerUserId = providerUserId,
        )
        applicationEventPublisher.publishEvent(NotificationLetter(saveUser, saveUser, "가입을 축하드려요!", ""))
        return saveUser
    }
}
