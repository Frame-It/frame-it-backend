package com.org.framelt.user.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.user.adapter.`in`.request.UserNicknameCheckRequest
import com.org.framelt.user.adapter.`in`.request.UserNicknameUpdateRequest
import com.org.framelt.user.adapter.`in`.request.UserProfileUpdateRequest
import com.org.framelt.user.adapter.`in`.request.UserQuitRequest
import com.org.framelt.user.adapter.`in`.response.*
import com.org.framelt.user.application.port.`in`.*
import com.org.framelt.user.common.UserMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    val userAccountReadUseCase: UserAccountReadUseCase,
    val userDeviseTokenUseCase: UserDeviseTokenUseCase,
    val userStudioUseCase: UserStudioUseCase,
    val userNicknameCheckUseCase: UserNicknameCheckUseCase,
    val userQuitUseCase: UserQuitUseCase,
    val userProfileUseCase: UserProfileUseCase,
) {
    @GetMapping("/users")
    fun getAccountInfo(
        @Authorization userId: Long,
    ): ResponseEntity<UserAccountInfoResponse> {
        val accountInfo = userAccountReadUseCase.getAccountInfo(userId)
        val response = UserAccountInfoResponse.from(accountInfo)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/users/{userId}/deviseToken")
    fun updateDeviseToken(
        @Authorization userId: Long,
        @RequestParam("deviseToken") deviseToken: String?,
    ): ResponseEntity<Void> {
        userDeviseTokenUseCase.updateDeviseToken(userId, deviseToken)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/users/{userId}/deviseToken")
    fun getDeviseToken(
        @Authorization userId: Long,
    ): ResponseEntity<UserDeviceTokenResponse> {
        val userDeviceTokenResponse = userDeviseTokenUseCase.getDeviseToken(userId)
        return ResponseEntity.ok(userDeviceTokenResponse)
    }

    @GetMapping("/users/studio")
    fun showMyStudio(
        @Authorization userId: Long,
    ): ResponseEntity<UserStudioResponse> {
        val result = userStudioUseCase.getStudio(userId)
        val response = UserMapper.toResponse(result)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/{userId}/studio")
    fun showGuestStudio(
        @PathVariable userId: Long,
    ): ResponseEntity<UserStudioResponse> {
        val result = userStudioUseCase.getStudio(userId)
        val response = UserMapper.toResponse(result)
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

    @GetMapping("/users/{userId}/in-progress-projects/exists")
    fun checkInProgressProjects(
        @PathVariable userId: Long,
    ): ResponseEntity<InProgressProjectsCheckResponse> {
        val exists = userQuitUseCase.hasInProgressProjects(userId)
        val response = InProgressProjectsCheckResponse(exists)
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

    @PatchMapping("/users/{updateUserId}")
    fun updateProfile(
        @PathVariable updateUserId: Long,
        @ModelAttribute userProfileUpdateRequest: UserProfileUpdateRequest,
        @Authorization userId: Long,
    ): ResponseEntity<Unit> {
        val userProfileUpdateCommand = UserMapper.toCommand(userProfileUpdateRequest, userId, updateUserId)
        userProfileUseCase.updateProfile(userProfileUpdateCommand)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/users/{updateUserId}/nickname")
    fun updateNickname(
        @PathVariable updateUserId: Long,
        @RequestBody userNicknameUpdateRequest: UserNicknameUpdateRequest,
        @Authorization userId: Long,
    ): ResponseEntity<Unit> {
        val userProfileUpdateCommand = UserMapper.toCommand(userNicknameUpdateRequest, userId, updateUserId)
        userProfileUseCase.updateNickname(userProfileUpdateCommand)
        return ResponseEntity.noContent().build()
    }
}
