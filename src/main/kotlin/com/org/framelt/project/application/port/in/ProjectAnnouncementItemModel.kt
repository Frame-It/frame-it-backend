package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Concept
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.domain.Identity
import java.time.LocalDateTime

data class ProjectAnnouncementItemModel(
    val id: Long,
    val previewImageUrl: String,
    val title: String,
    val recruitmentRole: Identity,
    val shootingAt: LocalDateTime,
    val spot: Spot,
    val timeOption: TimeOption,
    val concepts: List<Concept>,
    val isBookmarked: Boolean,
)
