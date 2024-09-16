package com.org.framelt.user.application.port.`in`

interface UserNicknameCheckUseCase {
    fun isNicknameDuplicated(userNicknameCheckCommand: UserNicknameCheckCommand): Boolean
}
