package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Concept
import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.domain.Identity
import java.time.LocalDateTime

data class BookmarkedProjectReadModel(
    val id: Long,
    val title: String,
    val recruitmentRole: Identity,
    val spot: Spot,
    val shootingAt: LocalDateTime,
    val timeOption: TimeOption,
    val concepts: List<Concept>,
) {
    companion object {
        fun fromDomain(project: Project): BookmarkedProjectReadModel =
            BookmarkedProjectReadModel(
                id = project.id!!,
                title = project.title,
                recruitmentRole = project.recruitmentRole,
                spot = project.spot,
                shootingAt = project.shootingAt,
                timeOption = project.timeOption,
                concepts = project.concepts,
            )
    }
}
