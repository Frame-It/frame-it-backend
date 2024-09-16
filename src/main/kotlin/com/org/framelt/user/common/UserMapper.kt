package com.org.framelt.user.common

import com.org.framelt.user.adapter.`in`.request.SignUpRequest
import com.org.framelt.user.adapter.`in`.request.UserNicknameCheckRequest
import com.org.framelt.user.adapter.`in`.request.UserQuitRequest
import com.org.framelt.user.application.port.`in`.SignUpCommand
import com.org.framelt.user.application.port.`in`.UserNicknameCheckCommand
import com.org.framelt.user.application.port.`in`.UserQuitCommand

class UserMapper {
    companion object {
        fun toCommand(
            signUpRequest: SignUpRequest,
            userId: Long,
        ): SignUpCommand =
            SignUpCommand(
                id = userId,
                identity = signUpRequest.identity,
                name = signUpRequest.name,
                birthDate = signUpRequest.birthDate,
                nickname = signUpRequest.nickname,
                notificationsEnabled = signUpRequest.notificationsEnabled,
            )

        fun toCommand(userNicknameCheckRequest: UserNicknameCheckRequest): UserNicknameCheckCommand =
            UserNicknameCheckCommand(
                nickname = userNicknameCheckRequest.nickname,
            )

        fun toCommand(
            userId: Long,
            quitRequest: UserQuitRequest,
        ): UserQuitCommand =
            UserQuitCommand(
                userId = userId,
                quitReason = quitRequest.quitReason,
            )
    }
}
