package com.org.framelt.user.adapter.`in`.request

import java.time.LocalDate

data class SignUpRequest(
    val identity: String,
    val name: String,
    val birthDate: LocalDate,
    val nickname: String,
    val notificationsEnabled: Boolean,
    val deviseToken: String?,
    val oauthUserId: Long,
)
