package com.org.framelt.user.adapter.`in`.request

import java.time.LocalDate

data class FakeLoginRequest(
    val name: String,
    val nickname: String,
    val identity: String,
    val birthDate: LocalDate,
    val notificationsEnabled: Boolean,
    val email: String,
)
