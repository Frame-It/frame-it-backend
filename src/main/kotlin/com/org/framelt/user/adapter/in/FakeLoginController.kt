package com.org.framelt.user.adapter.`in`

import com.org.framelt.user.adapter.`in`.request.FakeLoginRequest
import com.org.framelt.user.adapter.`in`.response.LoginResponse
import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.adapter.out.persistence.OAuthUserJpaEntity
import com.org.framelt.user.adapter.out.persistence.OAuthUserJpaRepository
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.UserJpaRepository
import com.org.framelt.user.application.port.out.JwtPort
import com.org.framelt.user.domain.Identity
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Profile(value = ["!prod"])
@RestController
class FakeLoginController(
    val oAuthUserJpaRepository: OAuthUserJpaRepository,
    val userJpaRepository: UserJpaRepository,
    val jwtPort: JwtPort,
) {
    @PostMapping("/fake/login")
    fun fakeLogin(
        @RequestBody request: FakeLoginRequest,
    ): ResponseEntity<LoginResponse> {
        val oauthUser =
            oAuthUserJpaRepository.findByEmail(request.email) ?: oAuthUserJpaRepository.save(
                OAuthUserJpaEntity(
                    provider = OAuthProvider.KAKAO,
                    providerUserId = "fake",
                    user =
                        userJpaRepository.save(
                            UserJpaEntity(
                                name = request.name,
                                nickname = request.nickname,
                                identity = Identity.of(request.identity),
                                birthDate = request.birthDate,
                                notificationsEnabled = request.notificationsEnabled,
                                email = request.email,
                                shootingConcepts = emptyList(),
                                isQuit = false,
                            ),
                        ),
                    email = request.email,
                ),
            )
        val user = oauthUser.user
        val response =
            LoginResponse(
                accessToken = jwtPort.createToken(user?.id.toString()),
                signUpCompleted = user != null,
                oauthUserId = oauthUser.id!!,
                identity = user?.identity!!.name,
            )
        return ResponseEntity.ok(response)
    }
}
