package com.org.framelt.user.application.port.`in`

data class UserQuitCommand(
    val userId: Long,
    val quitReason: String?,
)
