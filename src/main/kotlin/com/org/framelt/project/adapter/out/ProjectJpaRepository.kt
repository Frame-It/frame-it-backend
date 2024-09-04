package com.org.framelt.project.adapter.out

import org.springframework.data.repository.Repository

interface ProjectJpaRepository : Repository<ProjectJpaEntity, Long> {
    fun save(project: ProjectJpaEntity): ProjectJpaEntity

    fun findById(id: Long): ProjectJpaEntity
}
