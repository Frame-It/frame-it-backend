package com.org.framelt.project.adapter.`in`.response

import java.time.LocalDateTime

data class ProjectAnnouncementItemResponse(
    val id: Long,
    val previewImageUrl: String,
    val title: String,
    val recruitmentRole: String,
    val shootingAt: LocalDateTime,
    val timeOption: String,
    val spot: String,
    val concepts: List<String>,
    val isBookmarked: Boolean,
)
