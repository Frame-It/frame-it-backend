package com.org.framelt.user.adapter.`in`

import com.org.framelt.user.adapter.`in`.response.LoginResponse
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val loginUseCase: LoginUseCase,
) {
    @GetMapping("/local/oauth2/code/kakao")
    fun loginWithKakao(
        @RequestParam code: String,
    ): ResponseEntity<LoginResponse> {
        val loginCommand = LoginCommand("kakao", code)
        val loginResult = loginUseCase.login(loginCommand)
        val response = LoginResponse(loginResult)
        return ResponseEntity.ok(response)
    }
}
