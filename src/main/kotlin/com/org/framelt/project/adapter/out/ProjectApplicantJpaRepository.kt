package com.org.framelt.project.adapter.out

import org.springframework.data.repository.Repository

interface ProjectApplicantJpaRepository : Repository<ProjectApplicantJpaEntity, Long> {
    fun save(projectApplicantJpaEntity: ProjectApplicantJpaEntity): ProjectApplicantJpaEntity

    fun findByProjectId(projectId: Long): List<ProjectApplicantJpaEntity>
}
