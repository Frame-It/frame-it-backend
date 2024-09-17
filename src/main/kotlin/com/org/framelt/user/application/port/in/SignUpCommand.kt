package com.org.framelt.user.application.port.`in`

import java.time.LocalDate

data class SignUpCommand(
    val id: Long,
    val identity: String,
    val name: String,
    val birthDate: LocalDate,
    val nickname: String,
    val notificationsEnabled: Boolean,
)
