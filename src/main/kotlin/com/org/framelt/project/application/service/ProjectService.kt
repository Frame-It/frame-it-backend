package com.org.framelt.project.application.service

import com.org.framelt.project.application.port.`in`.ProjectApplyCommand
import com.org.framelt.project.application.port.`in`.ProjectApplyModel
import com.org.framelt.project.application.port.`in`.ProjectApplyUseCase
import com.org.framelt.project.application.port.`in`.ProjectCreateCommand
import com.org.framelt.project.application.port.`in`.ProjectCreateUseCase
import com.org.framelt.project.application.port.`in`.ProjectDetailModel
import com.org.framelt.project.application.port.`in`.ProjectFilterCommand
import com.org.framelt.project.application.port.`in`.ProjectItemModel
import com.org.framelt.project.application.port.`in`.ProjectReadUseCase
import com.org.framelt.project.application.port.`in`.ProjectUpdateCommand
import com.org.framelt.project.application.port.`in`.ProjectUpdateUseCase
import com.org.framelt.project.application.port.out.ProjectApplicantCommandPort
import com.org.framelt.project.application.port.out.ProjectCommandPort
import com.org.framelt.project.application.port.out.ProjectQueryPort
import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service

@Service
class ProjectService(
    val projectCommandPort: ProjectCommandPort,
    val projectQueryPort: ProjectQueryPort,
    val userQueryPort: UserQueryPort,
    val projectApplicantCommandPort: ProjectApplicantCommandPort,
) : ProjectCreateUseCase,
    ProjectUpdateUseCase,
    ProjectReadUseCase,
    ProjectApplyUseCase {
    override fun create(projectCreateCommand: ProjectCreateCommand): Long {
        val manager = userQueryPort.readById(projectCreateCommand.userId)
        val project =
            Project(
                manager = manager,
                title = projectCreateCommand.title,
                recruitmentRole = projectCreateCommand.recruitmentRole,
                shootingAt = projectCreateCommand.shootingAt,
                timeOption = projectCreateCommand.timeOption,
                locationType = projectCreateCommand.locationType,
                spot = projectCreateCommand.spot,
                concepts = projectCreateCommand.concepts,
                conceptPhotoUrls = projectCreateCommand.conceptPhotoUrls,
                description = projectCreateCommand.description,
                retouchingDescription = projectCreateCommand.retouchingDescription,
                applicantIds = mutableListOf(),
            )
        val savedProject = projectCommandPort.save(project)
        return savedProject.id!!
    }

    override fun getProjectDetail(projectId: Long): ProjectDetailModel {
        val project = projectQueryPort.readById(projectId)
        return ProjectDetailModel(
            id = project.id!!,
            title = project.title,
            description = project.description,
            shootingAt = project.shootingAt,
            locationType = project.locationType,
            spot = project.spot,
            concepts = project.concepts,
            conceptPhotoUrls = project.conceptPhotoUrls,
            retouchingDescription = project.retouchingDescription,
            managerId = project.manager.id!!,
            managerNickname = project.manager.nickname,
            managerProfileImageUrl = project.manager.profileImageUrl,
            managerDescription = project.manager.description,
        )
    }

    override fun getProjectList(projectFilterCommand: ProjectFilterCommand): List<ProjectItemModel> {
        val projects = projectQueryPort.readAll(projectFilterCommand)
        return projects.map {
            ProjectItemModel(
                id = it.id!!,
                previewImageUrl = it.conceptPhotoUrls.first(),
                title = it.title,
                recruitmentRole = it.recruitmentRole,
                shootingAt = it.shootingAt,
                spot = it.spot,
                timeOption = it.timeOption,
                concepts = it.concepts,
            )
        }
    }

    override fun update(projectUpdateCommand: ProjectUpdateCommand): Long {
        val project = projectQueryPort.readById(projectUpdateCommand.projectId)
        val updatedProject =
            project.update(
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
        val project = projectQueryPort.readById(projectApplyCommand.projectId)
        val applicant = userQueryPort.readById(projectApplyCommand.applicantId)
        val projectApplicant =
            ProjectApplicant(
                project = project,
                applicant = applicant,
            )
        projectApplicantCommandPort.save(projectApplicant)
        // TODO: 프로젝트 호스트에게 신청 알림 전송
        return ProjectApplyModel(project.title)
    }
}
