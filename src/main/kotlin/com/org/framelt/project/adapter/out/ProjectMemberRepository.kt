package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.out.ProjectMemberCommandPort
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.Status
import org.springframework.stereotype.Repository

@Repository
class ProjectMemberRepository(
    val projectMemberJpaRepository: ProjectMemberJpaRepository,
) : ProjectMemberCommandPort,
    ProjectMemberQueryPort {
    override fun save(projectMember: ProjectMember) {
        projectMemberJpaRepository.save(ProjectMemberJpaEntity.fromDomain(projectMember))
    }

    override fun readAllByProjectId(projectId: Long): List<ProjectMember> =
        projectMemberJpaRepository.findByProjectId(projectId).map { it.toDomain() }

    override fun readByMemberIdAndProjectId(
        memberId: Long,
        projectId: Long,
    ): ProjectMember = projectMemberJpaRepository.findByMemberIdAndProjectId(memberId, projectId).toDomain()

    override fun existsByMemberIdAndProjectStatus(
        memberId: Long,
        status: Status,
    ): Boolean = projectMemberJpaRepository.existsByMemberIdAndProjectStatus(memberId, status)

    override fun readAllByUserId(userId: Long): List<ProjectMember> =
        projectMemberJpaRepository.findByMemberId(userId).map { it.toDomain() }
}
