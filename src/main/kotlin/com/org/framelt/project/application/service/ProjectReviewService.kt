package com.org.framelt.project.application.service

import com.org.framelt.project.application.port.`in`.ProjectReviewCommand
import com.org.framelt.project.application.port.`in`.ProjectReviewCreateUseCase
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.application.port.out.ProjectReviewCommandPort
import com.org.framelt.project.domain.ProjectReview
import org.springframework.stereotype.Service

@Service
class ProjectReviewService(
    val projectMemberQueryPort: ProjectMemberQueryPort,
    val projectReviewCommandPort: ProjectReviewCommandPort,
) : ProjectReviewCreateUseCase {
    override fun review(projectReviewCommand: ProjectReviewCommand) {
        val reviewer = projectMemberQueryPort.readByMemberIdAndProjectId(projectReviewCommand.reviewerId, projectReviewCommand.projectId)
        val reviewee = projectMemberQueryPort.readByMemberIdAndProjectId(projectReviewCommand.revieweeId, projectReviewCommand.projectId)

        val projectReview =
            ProjectReview(
                reviewer = reviewer,
                reviewee = reviewee,
                tags = projectReviewCommand.tags,
                content = projectReviewCommand.content,
            )
        projectReviewCommandPort.save(projectReview)
    }
}
