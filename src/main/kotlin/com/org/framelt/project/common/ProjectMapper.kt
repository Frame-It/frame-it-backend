package com.org.framelt.project.common

import com.org.framelt.project.adapter.`in`.request.ProjectApplicationCancelRequest
import com.org.framelt.project.adapter.`in`.request.ProjectApplyRequest
import com.org.framelt.project.adapter.`in`.request.ProjectCreateRequest
import com.org.framelt.project.adapter.`in`.request.ProjectReviewRequest
import com.org.framelt.project.adapter.`in`.request.ProjectUpdateRequest
import com.org.framelt.project.adapter.`in`.response.ProjectAnnouncementDetailResponse
import com.org.framelt.project.adapter.`in`.response.ProjectAnnouncementItemResponse
import com.org.framelt.project.adapter.`in`.response.ProjectDetailManagerResponse
import com.org.framelt.project.adapter.`in`.response.UserProjectItemResponse
import com.org.framelt.project.adapter.`in`.response.UserProjectsResponse
import com.org.framelt.project.application.port.`in`.BookmarkedProjectReadModel
import com.org.framelt.project.application.port.`in`.ProjectAnnouncementDetailModel
import com.org.framelt.project.application.port.`in`.ProjectAnnouncementItemModel
import com.org.framelt.project.application.port.`in`.ProjectApplicantCancelCommand
import com.org.framelt.project.application.port.`in`.ProjectApplyCommand
import com.org.framelt.project.application.port.`in`.ProjectCreateCommand
import com.org.framelt.project.application.port.`in`.ProjectFilterCommand
import com.org.framelt.project.application.port.`in`.ProjectReviewCommand
import com.org.framelt.project.application.port.`in`.ProjectReviewReadCommand
import com.org.framelt.project.application.port.`in`.ProjectUpdateCommand
import com.org.framelt.project.application.port.`in`.UserProjectReadCommand
import com.org.framelt.project.application.port.`in`.UserProjectsModel
import com.org.framelt.project.domain.ProjectApplicantCancelReason
import com.org.framelt.project.domain.ProjectConcept
import com.org.framelt.project.domain.ProjectReviewTag
import com.org.framelt.project.domain.Status
import com.org.framelt.user.domain.Identity
import java.time.LocalDate

class ProjectMapper {
    companion object {
        fun toCommand(
            userId: Long,
            request: ProjectCreateRequest,
        ): ProjectCreateCommand =
            ProjectCreateCommand(
                userId = userId,
                title = request.title,
                recruitmentRole = Identity.of(request.recruitmentRole),
                shootingAt = request.shootingAt,
                timeOption = request.timeOption,
                locationType = request.locationType,
                spot = request.spot,
                concepts = request.concepts.map { ProjectConcept.fromCode(it) },
                conceptPhotos = request.conceptPhotos,
                description = request.description,
                retouchingDescription = request.retouchingDescription,
            )

        fun toCommand(
            userId: Long,
            projectId: Long,
            request: ProjectUpdateRequest,
        ): ProjectUpdateCommand =
            ProjectUpdateCommand(
                userId = userId,
                projectId = projectId,
                title = request.title,
                shootingAt = request.shootingAt,
                timeOption = request.timeOption,
                locationType = request.locationType,
                spot = request.spot,
                concepts = request.concepts.map { ProjectConcept.fromCode(it) },
                conceptPhotos = request.conceptPhotos,
                description = request.description,
                retouchingDescription = request.retouchingDescription,
            )

        fun toCommand(
            recruitmentRole: String?,
            startDate: LocalDate?,
            endDate: LocalDate?,
            timeOption: String?,
            spot: String?,
            locationType: String?,
            concepts: List<String>?,
            userId: Long,
        ): ProjectFilterCommand =
            ProjectFilterCommand(
                recruitmentRole = recruitmentRole,
                startDate = startDate,
                endDate = endDate,
                timeOption = timeOption,
                spot = spot,
                locationType = locationType,
                concepts = concepts?.map { ProjectConcept.fromCode(it) },
                userId = userId,
            )

        fun toResponse(projectDetail: ProjectAnnouncementDetailModel): ProjectAnnouncementDetailResponse =
            ProjectAnnouncementDetailResponse(
                id = projectDetail.id,
                title = projectDetail.title,
                shootingAt = projectDetail.shootingAt,
                locationType = projectDetail.locationType.name,
                spot = projectDetail.spot.name,
                hostConcepts = projectDetail.hostConcepts.map { it.code },
                projectConcepts = projectDetail.projectConcepts.map { it.code },
                conceptPhotoUrls = projectDetail.conceptPhotoUrls,
                description = projectDetail.description,
                retouchingDescription = projectDetail.retouchingDescription,
                host =
                    ProjectDetailManagerResponse(
                        id = projectDetail.host,
                        nickname = projectDetail.hostNickname,
                        profileImageUrl = projectDetail.hostProfileImageUrl,
                        description = projectDetail.hostDescription,
                        identity = projectDetail.hostIdentity.name,
                    ),
                isBookmarked = projectDetail.isBookmarked,
                isClosed = projectDetail.isClosed,
                isHost = projectDetail.isHost,
                timeOption = projectDetail.timeOption.name,
            )

        fun toResponse(projectItem: ProjectAnnouncementItemModel): ProjectAnnouncementItemResponse =
            ProjectAnnouncementItemResponse(
                id = projectItem.id,
                previewImageUrl = projectItem.previewImageUrl,
                title = projectItem.title,
                recruitmentRole = projectItem.recruitmentRole.name,
                shootingAt = projectItem.shootingAt,
                spot = projectItem.spot.name,
                timeOption = projectItem.timeOption.name,
                concepts = projectItem.concepts.map { it.code },
                isBookmarked = projectItem.isBookmarked,
            )

        fun toCommand(
            projectId: Long,
            userId: Long,
            projectApplyRequest: ProjectApplyRequest,
        ): ProjectApplyCommand =
            ProjectApplyCommand(
                projectId = projectId,
                applicantId = userId,
                applyContent = projectApplyRequest.applyContent,
            )

        fun toCommand(
            projectId: Long,
            applicantId: Long,
            projectApplicationCancelRequest: ProjectApplicationCancelRequest,
        ): ProjectApplicantCancelCommand =
            ProjectApplicantCancelCommand(
                projectId = projectId,
                applicantId = applicantId,
                cancelReason = projectApplicationCancelRequest.cancelReasons.map { ProjectApplicantCancelReason.fromCode(it) },
                content = projectApplicationCancelRequest.content,
            )

        fun toCommand(
            projectId: Long,
            reviewerId: Long,
            projectReviewRequest: ProjectReviewRequest,
        ): ProjectReviewCommand =
            ProjectReviewCommand(
                projectId = projectId,
                reviewerId = reviewerId,
                revieweeId = projectReviewRequest.revieweeId,
                tags = projectReviewRequest.tags.map { ProjectReviewTag.fromCode(it) },
                content = projectReviewRequest.content,
            )

        fun toCommand(
            reviewId: Long,
            userId: Long,
        ): ProjectReviewReadCommand =
            ProjectReviewReadCommand(
                reviewId = reviewId,
                userId = userId,
            )

        fun toResponse(userProjects: UserProjectsModel): UserProjectsResponse =
            UserProjectsResponse(
                nickname = userProjects.nickname,
                projects =
                    userProjects.projects.map {
                        UserProjectItemResponse(
                            id = it.id,
                            title = it.title,
                            shootingAt = it.shootingAt,
                            timeOption = it.timeOption.name,
                            spot = it.spot.name,
                            status = it.status.name,
                            isHost = it.isHost,
                        )
                    },
            )

        fun toCommand(
            viewerId: Long,
            userId: Long,
            status: String?,
            includesApplicant: Boolean,
        ): UserProjectReadCommand =
            UserProjectReadCommand(
                viewerId = viewerId,
                userId = userId,
                status = Status.fromNullable(status),
                includesApplicant = includesApplicant,
            )

        fun toResponse(bookmarkedProjects: List<BookmarkedProjectReadModel>): List<ProjectAnnouncementItemResponse> =
            bookmarkedProjects.map {
                ProjectAnnouncementItemResponse(
                    id = it.id,
                    previewImageUrl = it.previewImageUrl,
                    title = it.title,
                    recruitmentRole = it.recruitmentRole.name,
                    shootingAt = it.shootingAt,
                    spot = it.spot.name,
                    timeOption = it.timeOption.name,
                    concepts = it.concepts.map { it.code },
                    isBookmarked = it.isBookmarked,
                )
            }
    }
}
