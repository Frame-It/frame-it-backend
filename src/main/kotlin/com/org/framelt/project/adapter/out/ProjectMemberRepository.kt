package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.out.ProjectMemberCommandPort
import com.org.framelt.project.domain.ProjectMember
import org.springframework.stereotype.Repository

@Repository
class ProjectMemberRepository(
    val projectMemberJpaRepository: ProjectMemberJpaRepository,
) : ProjectMemberCommandPort {
    override fun save(projectMember: ProjectMember) {
        projectMemberJpaRepository.save(ProjectMemberJpaEntity.fromDomain(projectMember))
    }
}
