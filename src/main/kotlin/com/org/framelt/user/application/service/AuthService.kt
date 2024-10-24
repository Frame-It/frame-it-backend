package com.org.framelt.user.application.service

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.adapter.out.persistence.OAuthUserQueryPort
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginResult
import com.org.framelt.user.application.port.`in`.LoginUseCase
import com.org.framelt.user.application.port.`in`.SignUpCommand
import com.org.framelt.user.application.port.`in`.SignUpResult
import com.org.framelt.user.application.port.`in`.SignUpUseCase
import com.org.framelt.user.application.port.`in`.UserNicknameCheckCommand
import com.org.framelt.user.application.port.out.JwtPort
import com.org.framelt.user.application.port.out.oauth.AuthPort
import com.org.framelt.user.application.port.out.persistence.OAuthUserCommandPort
import com.org.framelt.user.application.port.out.persistence.OAuthUserModel
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Service

@Service
class AuthService(
    val userService: UserService,
    val authPort: AuthPort,
    val jwtPort: JwtPort,
    val userCommandPort: UserCommandPort,
    val oauthUserQueryPort: OAuthUserQueryPort,
    val oauthUserCommandPort: OAuthUserCommandPort,
) : LoginUseCase,
    SignUpUseCase {
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

    override fun signUp(signUpCommand: SignUpCommand): SignUpResult {
        val oauthUser = oauthUserQueryPort.readById(signUpCommand.oauthUserId)
        require(oauthUser.user == null) { "이미 가입이 완료된 사용자입니다." }
        require(!userService.isNicknameDuplicated(UserNicknameCheckCommand(signUpCommand.nickname))) { "이미 존재하는 닉네임은 사용할 수 없습니다." }

        val user =
            User(
                identity = signUpCommand.identity,
                name = signUpCommand.name,
                birthDate = signUpCommand.birthDate,
                nickname = signUpCommand.nickname,
                notificationsEnabled = signUpCommand.notificationsEnabled,
                email = oauthUser.email,
                deviseToken = signUpCommand.deviseToken,
                shootingConcepts = emptyList(),
            )
        val savedUser = userCommandPort.save(user)
        val signupCompletedOauthUSer = oauthUser.completeSignup(savedUser)
        oauthUserCommandPort.save(signupCompletedOauthUSer)
        return SignUpResult(
            accessToken = jwtPort.createToken(savedUser.id.toString()),
            identity = savedUser.identity,
        )
    }
}
