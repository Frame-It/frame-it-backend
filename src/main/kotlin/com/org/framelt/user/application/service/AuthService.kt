package com.org.framelt.user.application.service

import com.org.framelt.notification.application.service.NotificationLetter
import com.org.framelt.notification.domain.NotificationEventType
import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.adapter.out.persistence.OAuthUserQueryPort
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginResult
import com.org.framelt.user.application.port.`in`.LoginUseCase
import com.org.framelt.user.application.port.`in`.RefreshTokenResult
import com.org.framelt.user.application.port.`in`.SignUpCommand
import com.org.framelt.user.application.port.`in`.SignUpResult
import com.org.framelt.user.application.port.`in`.SignUpUseCase
import com.org.framelt.user.application.port.`in`.UserNicknameCheckCommand
import com.org.framelt.user.application.port.out.JwtPort
import com.org.framelt.user.application.port.out.oauth.AuthPort
import com.org.framelt.user.application.port.out.persistence.OAuthUserCommandPort
import com.org.framelt.user.application.port.out.persistence.OAuthUserModel
import com.org.framelt.user.application.port.out.persistence.RefreshToken
import com.org.framelt.user.application.port.out.persistence.RefreshTokenCommandPort
import com.org.framelt.user.application.port.out.persistence.RefreshTokenQueryPort
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.User
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    val userService: UserService,
    val authPort: AuthPort,
    val jwtPort: JwtPort,
    val userCommandPort: UserCommandPort,
    val oauthUserQueryPort: OAuthUserQueryPort,
    val oauthUserCommandPort: OAuthUserCommandPort,
    val applicationEventPublisher: ApplicationEventPublisher,
    val refreshTokenQueryPort: RefreshTokenQueryPort,
    val refreshTokenCommandPort: RefreshTokenCommandPort,
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
            accessToken = jwtPort.createAccessToken(user?.id.toString()),
            refreshToken = createRefreshToken(user?.id),
            signUpCompleted = user != null,
            oauthUserId = oauthUser.id!!,
            identity = user?.identity ?: Identity.NONE,
            notificationsEnabled = user?.notificationsEnabled,
            id = user?.id,
        )
    }

    fun createRefreshToken(userId: Long?): String {
        val previousRefreshTokens = refreshTokenQueryPort.findAllByUserId(userId!!).filter { it.isValid }
        check(previousRefreshTokens.size <= 1) { "유효한 리프레시 토큰은 2개 이상 존재할 수 없습니다." }
        val previousRefreshToken = previousRefreshTokens.firstOrNull()
        previousRefreshToken?.let {
            it.invalidate()
            refreshTokenCommandPort.save(it)
        }

        val refreshToken =
            RefreshToken(
                userId = userId,
                token = jwtPort.createRefreshToken(userId.toString()),
            )
        refreshTokenCommandPort.save(refreshToken)
        return refreshToken.token
    }

    override fun refreshToken(refreshToken: String): RefreshTokenResult {
        val refreshTokenEntity = refreshTokenQueryPort.findByToken(refreshToken)
        requireNotNull(refreshTokenEntity) { "유효하지 않은 리프레시 토큰입니다." }
        if (!refreshTokenEntity.isValid) {
            refreshTokenCommandPort.deleteAllByUserId(refreshTokenEntity.userId)
            throw IllegalArgumentException("새로 발급된 리프레시 토큰이 존재하여 탈취된 것으로 간주합니다. 다시 로그인해주세요.")
        }

        val claims = jwtPort.parseTokenWithoutScheme(refreshToken)
        require(claims["type"] == "refresh") { "리프레시 토큰이 아닙니다." }

        val newRefreshToken = createRefreshToken(refreshTokenEntity.userId)
        val newAccessToken = jwtPort.createAccessToken(refreshTokenEntity.userId.toString())
        return RefreshTokenResult(newAccessToken, newRefreshToken)
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
        applicationEventPublisher.publishEvent(
            NotificationLetter(
                sender = savedUser,
                receiver = savedUser,
                title = "프레이밋 가입을 축하해요!",
                content = "",
                id = savedUser.id!!,
                projectStatus = null,
                isHost = null,
                eventType = NotificationEventType.SIGN_UP,
                time = LocalDateTime.now(),
            ),
        )
        return SignUpResult(
            accessToken = jwtPort.createAccessToken(savedUser.id.toString()),
            refreshToken = createRefreshToken(savedUser.id),
            identity = savedUser.identity,
        )
    }
}
