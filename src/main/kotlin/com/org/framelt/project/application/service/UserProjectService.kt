package com.org.framelt.project.application.service

import com.org.framelt.project.application.port.`in`.UserProjectModel
import com.org.framelt.project.application.port.`in`.UserProjectReadCommand
import com.org.framelt.project.application.port.`in`.UserProjectUseCase
import com.org.framelt.project.application.port.`in`.UserProjectsModel
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.application.port.out.ProjectQueryPort
import com.org.framelt.project.domain.Status
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import org.springframework.stereotype.Service

@Service
class UserProjectService(
    val projectQueryPort: ProjectQueryPort,
    val projectMemberQueryPort: ProjectMemberQueryPort,
    val userQueryPort: UserQueryPort,
) : UserProjectUseCase {
    override fun readProjectsByUserId(userProjectReadCommand: UserProjectReadCommand): UserProjectsModel {
        val user = userQueryPort.readById(userProjectReadCommand.userId)
        val recruitingProjects = projectQueryPort.readByHostIdAndStatus(userProjectReadCommand.userId, Status.RECRUITING)
        val inProgressOrCompletedProjects = projectMemberQueryPort.readAllByUserId(userProjectReadCommand.userId).map { it.project }
        val userProjects = recruitingProjects + inProgressOrCompletedProjects.sortedByDescending { it.id }

        val filteredProjects =
            userProjectReadCommand.status?.let { status ->
                userProjects.filter { it.status == status }
            } ?: userProjects

        val projects =
            filteredProjects.map {
                UserProjectModel(
                    id = it.id!!,
                    title = it.title,
                    shootingAt = it.shootingAt,
                    timeOption = it.timeOption,
                    spot = it.spot,
                    status = it.status,
                    isHost = it.host == user,
                )
            }

        return UserProjectsModel(
            nickname = user.nickname,
            projects = projects,
        )
    }
}
