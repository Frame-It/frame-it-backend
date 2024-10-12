package com.org.framelt.user.adapter.`in`

import com.org.framelt.user.adapter.`in`.response.LoginResponse
import com.org.framelt.user.application.port.`in`.LoginCommand
import com.org.framelt.user.application.port.`in`.LoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val loginUseCase: LoginUseCase,
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
                identity = loginResult.identity.name,
            )
        return ResponseEntity.ok(response)
    }
}
