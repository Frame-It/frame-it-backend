package com.org.framelt.user.application.port.`in`

data class UserAccountInfoModel(
    val name: String,
    val nickname: String,
    val email: String,
    val notificationsEnabled: Boolean,
)
