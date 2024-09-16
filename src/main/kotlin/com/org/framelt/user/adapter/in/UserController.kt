package com.org.framelt.user.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.user.adapter.`in`.request.SignUpRequest
import com.org.framelt.user.adapter.`in`.request.UserNicknameCheckRequest
import com.org.framelt.user.adapter.`in`.request.UserQuitRequest
import com.org.framelt.user.adapter.`in`.response.UserAccountInfoResponse
import com.org.framelt.user.adapter.`in`.response.UserNicknameCheckResponse
import com.org.framelt.user.application.port.`in`.SignUpUseCase
import com.org.framelt.user.application.port.`in`.UserAccountReadUseCase
import com.org.framelt.user.application.port.`in`.UserNicknameCheckUseCase
import com.org.framelt.user.application.port.`in`.UserQuitUseCase
import com.org.framelt.user.common.UserMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    val signUpUseCase: SignUpUseCase,
    val userAccountReadUseCase: UserAccountReadUseCase,
    val userNicknameCheckUseCase: UserNicknameCheckUseCase,
    val userQuitUseCase: UserQuitUseCase,
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

    @GetMapping("/users")
    fun getAccountInfo(
        @Authorization userId: Long,
    ): ResponseEntity<UserAccountInfoResponse> {
        val accountInfo = userAccountReadUseCase.getAccountInfo(userId)
        val response = UserAccountInfoResponse.from(accountInfo)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/users/nicknames/check")
    fun checkNicknameDuplication(
        @RequestBody userNicknameCheckRequest: UserNicknameCheckRequest,
    ): ResponseEntity<UserNicknameCheckResponse> {
        val userNicknameCheckCommand = UserMapper.toCommand(userNicknameCheckRequest)
        val isDuplicated = userNicknameCheckUseCase.isNicknameDuplicated(userNicknameCheckCommand)
        val response = UserNicknameCheckResponse(isDuplicated)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/users")
    fun quit(
        @Authorization userId: Long,
        @RequestBody(required = false) userQuitRequest: UserQuitRequest,
    ): ResponseEntity<Unit> {
        val quitCommand = UserMapper.toCommand(userId, userQuitRequest)
        userQuitUseCase.quit(quitCommand)
        return ResponseEntity.ok().build()
    }
}
