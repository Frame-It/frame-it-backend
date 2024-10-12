package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.ProjectReview
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.Status
import com.org.framelt.project.domain.TimeOption
import java.time.LocalDateTime

data class CompletedProjectDetailGuestModel(
    val title: String,
    val spot: Spot,
    val timeOption: TimeOption,
    val shootingAt: LocalDateTime,
    val status: Status,
    val isReviewDone: Boolean,
    val reviewId: Long?,
    val isHost: Boolean,
    val projectMember: CompletedProjectMemberModel,
) {
    companion object {
        fun fromDomain(
            project: Project,
            hostProjectReview: ProjectReview?,
            guest: ProjectMember,
            guestProjectReview: ProjectReview?,
        ) = CompletedProjectDetailGuestModel(
            title = project.title,
            spot = project.spot,
            timeOption = project.timeOption,
            shootingAt = project.shootingAt,
            status = project.status,
            isReviewDone = hostProjectReview != null,
            reviewId = hostProjectReview?.id,
            isHost = !guest.isHost,
            projectMember = CompletedProjectMemberModel.fromDomain(guest, guestProjectReview),
        )
    }
}
