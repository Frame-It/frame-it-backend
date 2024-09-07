package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Concept
import com.org.framelt.project.domain.Spot
import com.org.framelt.user.domain.LocationType
import java.time.LocalDateTime

data class ProjectAnnouncementDetailModel(
    val id: Long,
    val title: String,
    val description: String,
    val shootingAt: LocalDateTime,
    val locationType: LocationType,
    val spot: Spot,
    val concepts: List<Concept>,
    val conceptPhotoUrls: List<String>,
    val retouchingDescription: String?,
    val managerId: Long,
    val managerNickname: String,
    val managerProfileImageUrl: String?,
    val managerDescription: String?,
)
