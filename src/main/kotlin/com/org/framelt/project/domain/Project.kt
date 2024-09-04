package com.org.framelt.project.domain

import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.LocationType
import com.org.framelt.user.domain.User
import java.time.LocalDateTime

data class Project(
    val id: Long? = null,
    val manager: User,
    val title: String,
    val recruitmentRole: Identity,
    val shootingAt: LocalDateTime,
    val locationType: LocationType,
    val spot: Spot,
    val concepts: List<Concept>,
    val conceptPhotoUrls: List<String>,
    val description: String,
    val retouchingDescription: String?,
    val applicantIds: List<Long>,
) {
    var status: Status = Status.모집중
        private set
}
