package com.org.framelt.project.adapter.`in`.request

import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.domain.LocationType
import java.time.LocalDateTime

data class ProjectUpdateRequest(
    val projectId: Long,
    val title: String,
    val shootingAt: LocalDateTime,
    val timeOption: TimeOption,
    val locationType: LocationType,
    val spot: Spot,
    val concepts: List<String>,
    val conceptPhotoUrls: List<String>,
    val description: String,
    val retouchingDescription: String,
)
