package com.org.framelt.user.adapter.`in`.response

data class LoginResponse(
    val accessToken: String?,
    val refreshToken: String?,
    val signUpCompleted: Boolean,
    val oauthUserId: Long,
    val identity: String,
    val notificationsEnabled: Boolean?,
    val id: Long?,
)
