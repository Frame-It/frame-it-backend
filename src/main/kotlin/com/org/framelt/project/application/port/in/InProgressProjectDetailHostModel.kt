package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.project.domain.ProjectReview
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.Status
import java.time.LocalDateTime

data class InProgressProjectDetailHostModel(
    val title: String,
    val spot: Spot,
    val shootingAt: LocalDateTime,
    val status: Status,
    val guest: GuestModel,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun fromDomain(
            project: Project,
            guest: ProjectApplicant,
            review: ProjectReview?,
        ): InProgressProjectDetailHostModel =
            InProgressProjectDetailHostModel(
                title = project.title,
                spot = project.spot,
                shootingAt = project.shootingAt,
                status = project.status,
                guest = GuestModel.fromDomain(guest),
                isReviewDone = review != null,
                reviewId = review?.id,
            )
    }
}
