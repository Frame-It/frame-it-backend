package com.org.framelt.user.application.port.`in`

interface UserProfileUseCase {
    fun updateProfile(command: UserProfileUpdateCommand)

    fun updateNickname(userNicknameUpdateCommand: UserNicknameUpdateCommand)
}
