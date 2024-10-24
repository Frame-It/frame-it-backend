package com.org.framelt.user.application.port.`in`

import com.org.framelt.user.domain.Identity
import java.time.LocalDate

data class SignUpCommand(
    val oauthUserId: Long,
    val identity: Identity,
    val name: String,
    val birthDate: LocalDate,
    val nickname: String,
    val notificationsEnabled: Boolean,
    val deviseToken: String?,
)
