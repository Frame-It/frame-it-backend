package com.org.framelt.project.adapter.`in`.response

import java.time.LocalDateTime

data class ProjectAnnouncementDetailResponse(
    val id: Long,
    val title: String,
    val description: String,
    val shootingAt: LocalDateTime,
    val locationType: String,
    val spot: String,
    val concepts: List<String>,
    val conceptPhotoUrls: List<String>,
    val retouchingDescription: String?,
    val host: ProjectDetailManagerResponse,
    val isBookmarked: Boolean,
    val isClosed: Boolean,
)
