package com.org.framelt.project.adapter.`in`.response

import java.time.LocalDateTime

data class UserProjectItemResponse(
    val id: Long,
    val title: String,
    val shootingAt: LocalDateTime,
    val timeOption: String,
    val spot: String,
    val status: String,
    val isHost: Boolean,
)
