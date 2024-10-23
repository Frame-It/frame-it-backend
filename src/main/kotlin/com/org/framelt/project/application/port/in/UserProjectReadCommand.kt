package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Status

data class UserProjectReadCommand(
    val viewerId: Long,
    val userId: Long,
    val status: Status?,
    val includesApplicant: Boolean,
)
