package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectConcept
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.LocationType
import com.org.framelt.user.domain.UserConcept
import java.time.LocalDateTime

data class ProjectAnnouncementDetailModel(
    val id: Long,
    val title: String,
    val description: String,
    val shootingAt: LocalDateTime,
    val locationType: LocationType,
    val timeOption: TimeOption,
    val spot: Spot,
    val hostConcepts: List<UserConcept>,
    val projectConcepts: List<ProjectConcept>,
    val conceptPhotoUrls: List<String>,
    val retouchingDescription: String?,
    val host: Long,
    val hostNickname: String,
    val hostIdentity: Identity,
    val hostProfileImageUrl: String?,
    val hostDescription: String?,
    val isBookmarked: Boolean,
    val isClosed: Boolean,
    val isHost: Boolean,
)
