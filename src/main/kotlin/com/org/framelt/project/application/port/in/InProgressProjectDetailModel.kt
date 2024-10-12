package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.ProjectReview
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.Status
import java.time.LocalDateTime

data class InProgressProjectDetailModel(
    val title: String,
    val spot: Spot,
    val shootingAt: LocalDateTime,
    val status: Status,
    val host: InProgressProjectHostModel,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun fromDomain(
            project: Project,
            projectMember: ProjectMember,
            review: ProjectReview?,
        ) = InProgressProjectDetailModel(
            title = project.title,
            spot = project.spot,
            shootingAt = project.shootingAt,
            status = project.status,
            host = InProgressProjectHostModel.fromDomain(projectMember),
            isReviewDone = review != null,
            reviewId = review?.id,
        )
    }
}
