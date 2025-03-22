package com.org.framelt.project.application.service

import com.org.framelt.notification.application.service.NotificationLetter
import com.org.framelt.notification.domain.NotificationEventType
import com.org.framelt.project.application.port.`in`.ProjectReviewCommand
import com.org.framelt.project.application.port.`in`.ProjectReviewCreateUseCase
import com.org.framelt.project.application.port.`in`.ProjectReviewModel
import com.org.framelt.project.application.port.`in`.ProjectReviewReadCommand
import com.org.framelt.project.application.port.`in`.ProjectReviewReadUseCase
import com.org.framelt.project.application.port.`in`.ProjectReviewResult
import com.org.framelt.project.application.port.`in`.UserProjectReviewReadCommand
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.application.port.out.ProjectReviewCommandPort
import com.org.framelt.project.application.port.out.ProjectReviewQueryPort
import com.org.framelt.project.domain.ProjectReview
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProjectReviewService(
    val projectMemberQueryPort: ProjectMemberQueryPort,
    val projectReviewCommandPort: ProjectReviewCommandPort,
    val projectReviewQueryPort: ProjectReviewQueryPort,
    val eventPublisher: ApplicationEventPublisher,
) : ProjectReviewCreateUseCase,
    ProjectReviewReadUseCase {
    override fun review(projectReviewCommand: ProjectReviewCommand): ProjectReviewResult {
        val reviewer = projectMemberQueryPort.readByMemberIdAndProjectId(projectReviewCommand.reviewerId, projectReviewCommand.projectId)
        val reviewee = projectMemberQueryPort.readByMemberIdAndProjectId(projectReviewCommand.revieweeId, projectReviewCommand.projectId)

        val projectReview =
            ProjectReview(
                reviewer = reviewer,
                reviewee = reviewee,
                tags = projectReviewCommand.tags,
                content = projectReviewCommand.content,
            )
        val project = reviewer.project

        val isRevieweeHost = project.host.equals(reviewee.member)
        val eventType = if (isRevieweeHost) NotificationEventType.PROJECT_REVIEW_CREATED_FOR_HOST else NotificationEventType.PROJECT_REVIEW_CREATED_FOR_APPLICANT
        eventPublisher.publishEvent(NotificationLetter(
            sender = reviewer.member,
            receiver = reviewee.member,
            title = "${reviewer.member.nickname}님이 리뷰를 남겼습니다.",
            content = "프로젝트: ${project.title}",
            projectStatus = project.status,
            id = project.id!!,
            isHost = isRevieweeHost,
            eventType = eventType,
            project = project,
            time = LocalDateTime.now(),
        ))
        return ProjectReviewResult(projectReviewCommandPort.save(projectReview).id!!, project.status)
    }

    override fun getProjectReview(projectReviewReadCommand: ProjectReviewReadCommand): ProjectReviewModel {
        val projectReview =
            projectReviewQueryPort.findById(projectReviewReadCommand.reviewId)
                ?: throw IllegalArgumentException("ID에 해당하는 프로젝트 리뷰가 존재하지 않습니다.")
        projectReview.validateReviewAccess(projectReviewReadCommand.userId)
        return ProjectReviewModel.fromDomain(projectReview)
    }

    override fun getProjectReviewsOfUser(userProjectReviewReadCommand: UserProjectReviewReadCommand): List<ProjectReviewModel> {
        require(userProjectReviewReadCommand.userId == userProjectReviewReadCommand.requestUserId) {
            "다른 유저의 리뷰 목록은 조회할 수 없습니다."
        }

        val projectReviews =
            projectReviewQueryPort.findAllByRevieweeUserId(userProjectReviewReadCommand.userId)
        return projectReviews.map { ProjectReviewModel.fromDomain(it) }
    }
}
