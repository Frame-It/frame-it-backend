package com.org.framelt.user.common

import com.org.framelt.user.adapter.`in`.request.SignUpRequest
import com.org.framelt.user.application.port.`in`.SignUpCommand

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
    }
}
