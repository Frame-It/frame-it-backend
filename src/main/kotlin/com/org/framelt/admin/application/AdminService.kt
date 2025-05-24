package com.org.framelt.admin.application

import com.org.framelt.admin.view.response.ProjectAdminResponse
import com.org.framelt.admin.view.response.ProjectHostResponse
import com.org.framelt.admin.view.response.UserAdminResponse
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.project.adapter.out.ProjectJpaRepository
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.domain.Status
import com.org.framelt.user.adapter.out.persistence.OAuthUserQueryPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val userQueryPort: UserQueryPort,
    private val oauthUserQueryPort: OAuthUserQueryPort,
    private val projectMemberQueryPort: ProjectMemberQueryPort,
    private val portfolioReadPort: PortfolioReadPort,
    private val projectJpaRepository: ProjectJpaRepository,
    @Value("\${admin.id}") private val adminId: String,
    @Value("\${admin.password}") private val adminPassword: String,
) {
    fun readAllUsers(): List<UserAdminResponse> {
        val users = userQueryPort.readAll()
        return users.map { user ->
            val oauthUser = oauthUserQueryPort.readByEmail(user.email)

            val participatedProjects = projectMemberQueryPort.readAllByUserId(user.id!!).map { it.project }
            val inProgressProjects = participatedProjects.filter { it.status == Status.IN_PROGRESS }
            val completedProjects = participatedProjects.filter { it.status == Status.COMPLETED }

            val portfolios = portfolioReadPort.readByUserId(user.id)

            UserAdminResponse(
                id = user.id,
                name = user.name,
                email = user.email,
                nickname = user.nickname,
                identity = user.identity.name,
                notificationsEnabled = user.notificationsEnabled,
                inProgressProjectCount = inProgressProjects.size,
                completedProjectCount = completedProjects.size,
                portfolioCount = portfolios.size,
                oauthType = oauthUser?.provider?.name ?: "NONE",
            )
        }.sortedBy { it.id }
    }

    fun login(
        id: String,
        password: String,
    ) {
        if (id != adminId || password != adminPassword) {
            throw IllegalArgumentException("관리자 아이디 또는 비밀번호가 일치하지 않습니다.")
        }
    }

    fun deleteProject(projectId: Long, httpSession: HttpSession) {
        if (httpSession.getAttribute("isAdmin") != true) {
            throw IllegalArgumentException("관리자 권한이 없습니다.")
        }

        projectJpaRepository.deleteById(projectId)
    }

    fun readAllProjects(): List<ProjectAdminResponse> = projectJpaRepository.findAll()
        .map { project ->
            ProjectAdminResponse(
                host = ProjectHostResponse(
                    nickname = project.host.nickname,
                    identity = project.host.identity.name,
                ),
                id = project.id!!,
                name = project.title,
                status = project.status.name,
                recruitmentRole = project.recruitmentRole.name,
            )
        }
}
