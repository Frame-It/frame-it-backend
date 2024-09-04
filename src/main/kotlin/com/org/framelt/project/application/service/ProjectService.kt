package com.org.framelt.project.application.service

import com.org.framelt.project.application.port.`in`.ProjectCreateCommand
import com.org.framelt.project.application.port.`in`.ProjectCreateUseCase
import com.org.framelt.project.application.port.out.ProjectCommandPort
import com.org.framelt.project.domain.Project
import com.org.framelt.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service

@Service
class ProjectService(
    val projectCommandPort: ProjectCommandPort,
    val userQueryPort: UserQueryPort,
) : ProjectCreateUseCase {
    override fun create(projectCreateCommand: ProjectCreateCommand): Long {
        val manager = userQueryPort.readById(projectCreateCommand.userId)
        val project =
            Project(
                manager = manager,
                title = projectCreateCommand.title,
                recruitmentRole = projectCreateCommand.recruitmentRole,
                shootingAt = projectCreateCommand.shootingAt,
                locationType = projectCreateCommand.locationType,
                spot = projectCreateCommand.spot,
                concepts = projectCreateCommand.concepts,
                conceptPhotoUrls = projectCreateCommand.conceptPhotoUrls,
                description = projectCreateCommand.description,
                retouchingDescription = projectCreateCommand.retouchingDescription,
                applicantIds = listOf(),
            )
        val savedProject = projectCommandPort.save(project)
        return savedProject.id!!
    }
}
