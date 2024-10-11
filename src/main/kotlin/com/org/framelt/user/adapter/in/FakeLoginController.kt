package com.org.framelt.user.adapter.`in`

import com.org.framelt.user.adapter.`in`.response.LoginResponse
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.UserJpaRepository
import com.org.framelt.user.application.port.out.JwtPort
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.User
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Profile(value = ["!prod"])
@RestController
class FakeLoginController(
    val userJpaRepository: UserJpaRepository,
    val jwtPort: JwtPort,
) {
    @GetMapping("/fake/login")
    fun fakeLogin(
        @RequestParam email: String,
    ): ResponseEntity<LoginResponse> {
        val user =
            userJpaRepository.findByEmail(email)
                ?: userJpaRepository.save(UserJpaEntity.fromDomain(User.beforeCompleteSignUp(email)))
        val response = LoginResponse(jwtPort.createToken(user.id.toString()), user.identity != Identity.NONE)
        return ResponseEntity.ok(response)
    }
}
