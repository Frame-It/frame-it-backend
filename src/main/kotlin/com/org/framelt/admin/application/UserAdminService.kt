package com.org.framelt.admin.application

import com.org.framelt.admin.view.response.UserAdminResponse
import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.domain.Status
import com.org.framelt.user.adapter.out.persistence.OAuthUserQueryPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import org.springframework.stereotype.Service

@Service
class UserAdminService(
    private val userQueryPort: UserQueryPort,
    private val oauthUserQueryPort: OAuthUserQueryPort,
    private val projectMemberQueryPort: ProjectMemberQueryPort,
    private val portfolioReadPort: PortfolioReadPort,
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
        }.sortedByDescending { it.id }
    }
}
