package com.org.framelt.user.application.service

import com.org.framelt.portfolio.application.port.out.PortfolioReadPort
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.application.port.out.ProjectQueryPort
import com.org.framelt.project.domain.Status
import com.org.framelt.user.application.port.`in`.UserStudioModel
import com.org.framelt.user.application.port.`in`.UserStudioUseCase
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import org.springframework.stereotype.Service

@Service
class UserStudioService(
    val userQueryPort: UserQueryPort,
    val projectQueryPort: ProjectQueryPort,
    val projectMemberQueryPort: ProjectMemberQueryPort,
    val portfolioReadPort: PortfolioReadPort,
) : UserStudioUseCase {
    override fun getStudio(userId: Long): UserStudioModel {
        val user = userQueryPort.readById(userId)
        val portfolios = portfolioReadPort.readByUserId(userId)
        val recruitingProjects = projectQueryPort.readByHostIdAndStatus(userId, Status.RECRUITING)
        val inProgressOrCompletedProjects = projectMemberQueryPort.readAllByUserId(userId).map { it.project }
        return UserStudioModel(
            id = user.id!!,
            nickname = user.nickname,
            identity = user.identity,
            profileImageUrl = user.profileImageUrl,
            portfolioCount = portfolios.size,
            projectCount = (recruitingProjects + inProgressOrCompletedProjects).size,
        )
    }
}
