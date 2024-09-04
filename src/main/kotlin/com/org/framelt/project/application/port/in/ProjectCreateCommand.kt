package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Concept
import com.org.framelt.project.domain.Spot
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.LocationType
import java.time.LocalDateTime

data class ProjectCreateCommand(
    val userId: Long,
    val title: String,
    val recruitmentRole: Identity,
    val shootingAt: LocalDateTime,
    val locationType: LocationType,
    val spot: Spot,
    val concepts: List<Concept>,
    val conceptPhotoUrls: List<String>,
    val description: String,
    val retouchingDescription: String,
)
