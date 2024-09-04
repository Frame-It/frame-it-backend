package com.org.framelt.project.adapter.`in`.request

import com.org.framelt.project.domain.Spot
import com.org.framelt.user.domain.LocationType
import java.time.LocalDateTime

data class ProjectCreateRequest(
    val userId: Long,
    val title: String,
    val recruitmentRole: String,
    val shootingAt: LocalDateTime,
    val locationType: LocationType,
    val spot: Spot,
    val concepts: List<String>,
    val conceptPhotoUrls: List<String>,
    val description: String,
    val retouchingDescription: String,
)
