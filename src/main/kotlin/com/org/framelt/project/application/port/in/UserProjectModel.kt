package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.Status
import com.org.framelt.project.domain.TimeOption
import java.time.LocalDateTime

data class UserProjectModel(
    val id: Long,
    val title: String,
    val shootingAt: LocalDateTime,
    val timeOption: TimeOption,
    val spot: Spot,
    val status: Status,
    val isHost: Boolean,
)
