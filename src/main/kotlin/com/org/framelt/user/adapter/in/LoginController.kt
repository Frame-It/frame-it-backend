package com.org.framelt.user.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.user.adapter.`in`.request.SignUpRequest
import com.org.framelt.user.adapter.`in`.response.LoginResponse
import com.org.framelt.user.adapter.`in`.response.SignUpResponse
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginUseCase
import com.org.framelt.user.application.port.`in`.SignUpUseCase
import com.org.framelt.user.common.UserMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
) {
    @GetMapping("/login/{socialType}")
    fun loginWithKakao(
        @PathVariable socialType: String,
        @RequestParam code: String,
        @RequestParam(value = "redirect_uri") redirectUri: String,
    ): ResponseEntity<LoginResponse> {
        val loginCommand = LoginCommand(socialType, code, redirectUri)
        val loginResult = loginUseCase.login(loginCommand)
        val response =
            LoginResponse(
                accessToken = loginResult.accessToken,
                signUpCompleted = loginResult.signUpCompleted,
                oauthUserId = loginResult.oauthUserId,
                identity = loginResult.identity.name,
            )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/users")
    fun signUp(
        @RequestBody signUpRequest: SignUpRequest,
    ): ResponseEntity<SignUpResponse> {
        val signUpCommand = UserMapper.toCommand(signUpRequest)
        val result = signUpUseCase.signUp(signUpCommand)
        val response =
            SignUpResponse(
                accessToken = result.accessToken,
                identity = result.identity.name,
            )
        return ResponseEntity.ok(response)
    }

    @GetMapping("/tokens/validate")
    fun validateToken(
        @Authorization userId: Long,
    ): ResponseEntity<Unit> = ResponseEntity.ok().build()
}
