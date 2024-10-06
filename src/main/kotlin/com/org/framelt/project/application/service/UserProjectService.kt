package com.org.framelt.project.application.service

import com.org.framelt.project.application.port.`in`.UserProjectModel
import com.org.framelt.project.application.port.`in`.UserProjectReadCommand
import com.org.framelt.project.application.port.`in`.UserProjectUseCase
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.application.port.out.ProjectQueryPort
import com.org.framelt.project.domain.Status
import org.springframework.stereotype.Service

@Service
class UserProjectService(
    val projectQueryPort: ProjectQueryPort,
    val projectMemberQueryPort: ProjectMemberQueryPort,
) : UserProjectUseCase {
    override fun readProjectsByUserId(userProjectReadCommand: UserProjectReadCommand): List<UserProjectModel> {
        require(userProjectReadCommand.userId == userProjectReadCommand.requestUserId) {
            "다른 유저가 참여한 프로젝트 목록은 조회할 수 없습니다."
        }
        val recruitingProjects = projectQueryPort.readByHostIdAndStatus(userProjectReadCommand.userId, Status.RECRUITING)
        val userProjects = projectMemberQueryPort.readAllByUserId(userProjectReadCommand.userId).map { it.project }
        return (recruitingProjects + userProjects).map {
            UserProjectModel(
                id = it.id!!,
                title = it.title,
                shootingAt = it.shootingAt,
                timeOption = it.timeOption,
                spot = it.spot,
                status = it.status,
            )
        }
    }
}
