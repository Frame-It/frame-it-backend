package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectConcept
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.domain.Identity
import java.time.LocalDateTime

data class BookmarkedProjectReadModel(
    val id: Long,
    val previewImageUrl: String?,
    val title: String,
    val recruitmentRole: Identity,
    val shootingAt: LocalDateTime,
    val spot: Spot,
    val timeOption: TimeOption,
    val concepts: List<ProjectConcept>,
    val isBookmarked: Boolean,
) {
    companion object {
        fun fromDomain(
            project: Project,
            previewImageUrl: String?,
            isBookmarked: Boolean,
        ): BookmarkedProjectReadModel =
            BookmarkedProjectReadModel(
                id = project.id!!,
                previewImageUrl = previewImageUrl,
                title = project.title,
                recruitmentRole = project.recruitmentRole,
                spot = project.spot,
                shootingAt = project.shootingAt,
                timeOption = project.timeOption,
                concepts = project.concepts,
                isBookmarked = isBookmarked,
            )
    }
}
