package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Status

data class UserProjectReadCommand(
    val userId: Long,
    val status: Status?,
)
