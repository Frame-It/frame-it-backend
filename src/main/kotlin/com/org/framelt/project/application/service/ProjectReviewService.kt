package com.org.framelt.project.application.service

import com.org.framelt.project.application.port.`in`.ProjectReviewCommand
import com.org.framelt.project.application.port.`in`.ProjectReviewCreateUseCase
import com.org.framelt.project.application.port.`in`.ProjectReviewModel
import com.org.framelt.project.application.port.`in`.ProjectReviewReadCommand
import com.org.framelt.project.application.port.`in`.ProjectReviewReadUseCase
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.application.port.out.ProjectReviewCommandPort
import com.org.framelt.project.application.port.out.ProjectReviewQueryPort
import com.org.framelt.project.domain.ProjectReview
import org.springframework.stereotype.Service

@Service
class ProjectReviewService(
    val projectMemberQueryPort: ProjectMemberQueryPort,
    val projectReviewCommandPort: ProjectReviewCommandPort,
    val projectReviewQueryPort: ProjectReviewQueryPort,
) : ProjectReviewCreateUseCase,
    ProjectReviewReadUseCase {
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

    override fun getProjectReview(projectReviewReadCommand: ProjectReviewReadCommand): ProjectReviewModel {
        val projectReview =
            projectReviewQueryPort.findById(projectReviewReadCommand.reviewId)
                ?: throw IllegalArgumentException("ID에 해당하는 프로젝트 리뷰가 존재하지 않습니다.")
        projectReview.validateReviewAccess(projectReviewReadCommand.userId)
        return ProjectReviewModel.fromDomain(projectReview)
    }
}
