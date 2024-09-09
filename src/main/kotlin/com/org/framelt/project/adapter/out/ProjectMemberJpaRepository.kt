package com.org.framelt.project.adapter.out

import org.springframework.data.repository.Repository

interface ProjectMemberJpaRepository : Repository<ProjectMemberJpaEntity, Long> {
    fun save(projectMemberJpaEntity: ProjectMemberJpaEntity)

    fun findByProjectId(projectId: Long): List<ProjectMemberJpaEntity>

    fun findByMemberIdAndProjectId(
        memberId: Long,
        projectId: Long,
    ): ProjectMemberJpaEntity
}
