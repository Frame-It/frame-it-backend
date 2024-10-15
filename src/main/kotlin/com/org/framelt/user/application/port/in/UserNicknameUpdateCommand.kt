package com.org.framelt.user.application.port.`in`

data class UserNicknameUpdateCommand(
    val userId: Long,
    val updateUserId: Long,
    val nickname: String,
)
