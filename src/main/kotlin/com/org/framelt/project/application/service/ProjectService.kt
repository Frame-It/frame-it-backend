package com.org.framelt.project.application.service

import com.org.framelt.portfolio.adapter.out.FileUploadClient
import com.org.framelt.project.application.port.`in`.CompletedProjectDetailModel
import com.org.framelt.project.application.port.`in`.InProgressProjectDetailModel
import com.org.framelt.project.application.port.`in`.ProjectAnnouncementDetailCommand
import com.org.framelt.project.application.port.`in`.ProjectAnnouncementDetailModel
import com.org.framelt.project.application.port.`in`.ProjectAnnouncementItemModel
import com.org.framelt.project.application.port.`in`.ProjectApplicantAcceptCommand
import com.org.framelt.project.application.port.`in`.ProjectApplicantCancelCommand
import com.org.framelt.project.application.port.`in`.ProjectApplyCommand
import com.org.framelt.project.application.port.`in`.ProjectApplyModel
import com.org.framelt.project.application.port.`in`.ProjectApplyUseCase
import com.org.framelt.project.application.port.`in`.ProjectCompleteCommand
import com.org.framelt.project.application.port.`in`.ProjectCompleteUseCase
import com.org.framelt.project.application.port.`in`.ProjectCreateCommand
import com.org.framelt.project.application.port.`in`.ProjectCreateUseCase
import com.org.framelt.project.application.port.`in`.ProjectFilterCommand
import com.org.framelt.project.application.port.`in`.ProjectReadUseCase
import com.org.framelt.project.application.port.`in`.ProjectUpdateCommand
import com.org.framelt.project.application.port.`in`.ProjectUpdateUseCase
import com.org.framelt.project.application.port.`in`.RecruitingProjectDetailGuestModel
import com.org.framelt.project.application.port.`in`.RecruitingProjectDetailHostModel
import com.org.framelt.project.application.port.out.ProjectApplicantCommandPort
import com.org.framelt.project.application.port.out.ProjectApplicantQueryPort
import com.org.framelt.project.application.port.out.ProjectBookmarkQueryPort
import com.org.framelt.project.application.port.out.ProjectCommandPort
import com.org.framelt.project.application.port.out.ProjectMemberCommandPort
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.application.port.out.ProjectQueryPort
import com.org.framelt.project.application.port.out.ProjectReviewQueryPort
import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.project.domain.ProjectClosureChecker
import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.Status
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
class ProjectService(
    val projectCommandPort: ProjectCommandPort,
    val projectQueryPort: ProjectQueryPort,
    val userQueryPort: UserQueryPort,
    val projectApplicantCommandPort: ProjectApplicantCommandPort,
    val projectApplicantQueryPort: ProjectApplicantQueryPort,
    val projectMemberCommandPort: ProjectMemberCommandPort,
    val projectMemberQueryPort: ProjectMemberQueryPort,
    val projectReviewQueryPort: ProjectReviewQueryPort,
    val projectBookmarkQueryPort: ProjectBookmarkQueryPort,
    val projectClosureChecker: ProjectClosureChecker,
    val fileUploadClient: FileUploadClient,
) : ProjectCreateUseCase,
    ProjectUpdateUseCase,
    ProjectReadUseCase,
    ProjectApplyUseCase,
    ProjectCompleteUseCase {
    override fun create(projectCreateCommand: ProjectCreateCommand): Long {
        val host = userQueryPort.readById(projectCreateCommand.userId)
        val conceptPhotoUrls =
            projectCreateCommand.conceptPhotos.map {
                fileUploadClient
                    .upload(it.originalFilename!!, MediaType.IMAGE_JPEG, it.bytes)
                    .orElseThrow { IllegalArgumentException("사진 업로드에 실패 했습니다. ${it.name}") }
            }
        val project =
            Project(
                host = host,
                title = projectCreateCommand.title,
                recruitmentRole = projectCreateCommand.recruitmentRole,
                shootingAt = projectCreateCommand.shootingAt,
                timeOption = projectCreateCommand.timeOption,
                locationType = projectCreateCommand.locationType,
                spot = projectCreateCommand.spot,
                concepts = projectCreateCommand.concepts,
                conceptPhotoUrls = conceptPhotoUrls,
                description = projectCreateCommand.description,
                retouchingDescription = projectCreateCommand.retouchingDescription,
            )
        val savedProject = projectCommandPort.save(project)
        return savedProject.id!!
    }

    override fun getProjectAnnouncementDetail(
        projectAnnouncementDetailCommand: ProjectAnnouncementDetailCommand,
    ): ProjectAnnouncementDetailModel {
        val project = projectQueryPort.readById(projectAnnouncementDetailCommand.projectId)
        val isBookmarked =
            projectBookmarkQueryPort.existsBookmark(
                projectId = projectAnnouncementDetailCommand.projectId,
                userId = projectAnnouncementDetailCommand.userId,
            )

        increaseViewCount(project)

        val applicantCount = projectApplicantQueryPort.countApplicants(project.id!!)
        return ProjectAnnouncementDetailModel(
            id = project.id,
            title = project.title,
            description = project.description,
            shootingAt = project.shootingAt,
            locationType = project.locationType,
            spot = project.spot,
            concepts = project.concepts,
            conceptPhotoUrls = project.conceptPhotoUrls,
            retouchingDescription = project.retouchingDescription,
            host = project.host.id!!,
            hostNickname = project.host.nickname,
            hostProfileImageUrl = project.host.profileImageUrl,
            hostDescription = project.host.description,
            isBookmarked = isBookmarked,
            isClosed =
                project.isClosed(
                    closureChecker = projectClosureChecker,
                    applicantCount = applicantCount,
                ),
        )
    }

    private fun increaseViewCount(project: Project) {
        project.increaseViewCount()
        projectCommandPort.save(project)
    }

    override fun getProjectAnnouncementList(projectFilterCommand: ProjectFilterCommand): List<ProjectAnnouncementItemModel> {
        val projects = projectQueryPort.readAll(projectFilterCommand)
        return projects.map {
            val isBookmarked = projectBookmarkQueryPort.existsBookmark(it.id!!, projectFilterCommand.userId)
            ProjectAnnouncementItemModel(
                id = it.id,
                previewImageUrl = it.conceptPhotoUrls.first(),
                title = it.title,
                recruitmentRole = it.recruitmentRole,
                shootingAt = it.shootingAt,
                spot = it.spot,
                timeOption = it.timeOption,
                concepts = it.concepts,
                isBookmarked = isBookmarked,
            )
        }
    }

    override fun getRecruitingProjectForHost(
        projectId: Long,
        userId: Long,
    ): RecruitingProjectDetailHostModel {
        // TODO: 호출자가 프로젝트 호스트인지 검증 추가
        val project = projectQueryPort.readById(projectId)
        validateProjectStatus(project, Status.RECRUITING)
        val applicants = projectApplicantQueryPort.readByProjectId(projectId)
        return RecruitingProjectDetailHostModel.fromDomain(project, applicants)
    }

    private fun validateProjectStatus(
        project: Project,
        status: Status,
    ) {
        if (project.status != status) {
            throw IllegalArgumentException(status.name + " 상태의 프로젝트가 아닙니다.")
        }
    }

    override fun getRecruitingProjectForGuest(
        projectId: Long,
        userId: Long,
    ): RecruitingProjectDetailGuestModel {
        // TODO: 호출자가 프로젝트 게스트인지 검증 추가
        val project = projectQueryPort.readById(projectId)
        validateProjectStatus(project, Status.RECRUITING)
        val applicant = projectApplicantQueryPort.readByProjectIdAndApplicantId(projectId, userId)
        return RecruitingProjectDetailGuestModel.fromDomain(project, applicant)
    }

    override fun getInProgressProject(
        projectId: Long,
        userId: Long,
    ): InProgressProjectDetailModel {
        val project = projectQueryPort.readById(projectId)
        validateProjectStatus(project, Status.IN_PROGRESS)

        val projectMembers = projectMemberQueryPort.readAllByProjectId(projectId)
        val otherProjectMember = projectMembers.first { it.member.id != userId }

        return InProgressProjectDetailModel.fromDomain(project, otherProjectMember)
    }

    override fun getCompletedProject(
        projectId: Long,
        userId: Long,
    ): CompletedProjectDetailModel {
        val project = projectQueryPort.readById(projectId)
        validateProjectStatus(project, Status.COMPLETED)

        val projectMembers = projectMemberQueryPort.readAllByProjectId(projectId)
        val projectMember = projectMembers.first { it.member.id == userId }
        val otherProjectMember = projectMembers.first { it.member.id != userId }

        val myProjectReview = projectReviewQueryPort.readByReviewerIdAndRevieweeId(projectMember.id!!, otherProjectMember.id!!)
        val projectReviewOfMember = projectReviewQueryPort.readByReviewerIdAndRevieweeId(otherProjectMember.id, projectMember.id)

        return CompletedProjectDetailModel.fromDomain(
            project = project,
            myProjectReview = myProjectReview,
            projectMember = otherProjectMember,
            projectReviewOfMember = projectReviewOfMember,
        )
    }

    override fun update(projectUpdateCommand: ProjectUpdateCommand): Long {
        val project = projectQueryPort.readById(projectUpdateCommand.projectId)
        val updatedProject =
            project.update(
                // TODO: 작성자 검증 추가
                title = projectUpdateCommand.title,
                shootingAt = projectUpdateCommand.shootingAt,
                timeOption = projectUpdateCommand.timeOption,
                locationType = projectUpdateCommand.locationType,
                spot = projectUpdateCommand.spot,
                concepts = projectUpdateCommand.concepts,
                conceptPhotoUrls = projectUpdateCommand.conceptPhotoUrls,
                description = projectUpdateCommand.description,
                retouchingDescription = projectUpdateCommand.retouchingDescription,
            )
        projectCommandPort.update(updatedProject)
        return updatedProject.id!!
    }

    override fun applyProject(projectApplyCommand: ProjectApplyCommand): ProjectApplyModel {
        validateDuplicateApplicationExistence(projectApplyCommand)

        val project = projectQueryPort.readById(projectApplyCommand.projectId)
        val applicant = userQueryPort.readById(projectApplyCommand.applicantId)
        validateProjectClosure(project)

        projectApplicantCommandPort.save(
            ProjectApplicant(
                project = project,
                applicant = applicant,
                applyContent = projectApplyCommand.applyContent,
            ),
        )
        // TODO: 프로젝트 호스트에게 신청 알림 전송
        return ProjectApplyModel(project.title)
    }

    private fun validateDuplicateApplicationExistence(projectApplyCommand: ProjectApplyCommand) {
        if (projectApplicantQueryPort.existsByProjectIdAndApplicantId(
                projectId = projectApplyCommand.projectId,
                applicantId = projectApplyCommand.applicantId,
            )
        ) {
            throw IllegalArgumentException("이미 지원한 프로젝트입니다.")
        }
    }

    private fun validateProjectClosure(project: Project) {
        if (project.isClosed(
                closureChecker = projectClosureChecker,
                applicantCount = projectApplicantQueryPort.countApplicants(project.id!!),
            )
        ) {
            throw IllegalArgumentException("모집이 마감된 프로젝트입니다.")
        }
    }

    override fun cancelApplication(projectApplicantCancelCommand: ProjectApplicantCancelCommand) {
        val projectApplicant =
            projectApplicantQueryPort.readByProjectIdAndApplicantId(
                projectId = projectApplicantCancelCommand.projectId,
                applicantId = projectApplicantCancelCommand.applicantId,
            )
        projectApplicant.cancel(projectApplicantCancelCommand.cancelReason)
        projectApplicantCommandPort.save(projectApplicant)
    }

    override fun acceptApplicant(projectApplicantAcceptCommand: ProjectApplicantAcceptCommand) {
        val projectApplicant =
            projectApplicantQueryPort.readByProjectIdAndApplicantId(
                projectId = projectApplicantAcceptCommand.projectId,
                applicantId = projectApplicantAcceptCommand.applicantId,
            )
        // TODO: 호출자가 프로젝트 호스트인지 검증 추가
        projectApplicant.accepted(projectApplicantAcceptCommand.projectId)

        val project = projectQueryPort.readById(projectApplicantAcceptCommand.projectId)
        val host =
            ProjectMember(
                project = project,
                member = project.host,
                isHost = true,
            )
        val guest =
            ProjectMember(
                project = project,
                member = projectApplicant.applicant,
            )
        projectMemberCommandPort.save(host)
        projectMemberCommandPort.save(guest)
        // TODO: (정책) 기존 지원자들 DB에서 삭제?

        project.start()
        projectCommandPort.save(project)
        // TODO: 프로젝트 호스트/게스트에게 시작 알림 전송
    }

    override fun complete(projectCompleteCommand: ProjectCompleteCommand) {
        val projectMember =
            projectMemberQueryPort.readByMemberIdAndProjectId(
                memberId = projectCompleteCommand.memberId,
                projectId = projectCompleteCommand.projectId,
            )
        projectMember.completeProject()
        projectMemberCommandPort.save(projectMember)

        val projectMembers = projectMemberQueryPort.readAllByProjectId(projectCompleteCommand.projectId)
        if (projectMembers.all { it.hasCompletedProject }) {
            val project = projectMember.project
            project.complete()
            projectCommandPort.save(project)
            // TODO: 프로젝트 호스트/게스트에게 완료 알림 전송
        }
    }
}
