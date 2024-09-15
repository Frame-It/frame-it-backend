package com.org.framelt.user.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.user.adapter.`in`.request.SignUpRequest
import com.org.framelt.user.application.port.`in`.SignUpUseCase
import com.org.framelt.user.common.UserMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    val signUpUseCase: SignUpUseCase,
) {
    @PutMapping("/users")
    fun signUp(
        @Authorization userId: Long,
        @RequestBody signUpRequest: SignUpRequest,
    ): ResponseEntity<Unit> {
        val signUpCommand = UserMapper.toCommand(signUpRequest, userId)
        signUpUseCase.signUp(signUpCommand)
        return ResponseEntity.ok().build()
    }
}
