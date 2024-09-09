package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.ProjectReview
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.Status
import com.org.framelt.project.domain.TimeOption
import java.time.LocalDateTime

data class CompletedProjectDetailModel(
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
            myProjectReview: ProjectReview?,
            projectMember: ProjectMember,
            projectReviewOfMember: ProjectReview?,
        ) = CompletedProjectDetailModel(
            title = project.title,
            spot = project.spot,
            timeOption = project.timeOption,
            shootingAt = project.shootingAt,
            status = project.status,
            isReviewDone = myProjectReview != null,
            reviewId = myProjectReview?.id,
            isHost = !projectMember.isHost,
            projectMember = CompletedProjectMemberModel.fromDomain(projectMember, projectReviewOfMember),
        )
    }
}
