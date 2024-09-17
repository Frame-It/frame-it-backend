package com.org.framelt.project.adapter.out

import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import org.springframework.data.repository.Repository

interface ProjectJpaRepository : Repository<ProjectJpaEntity, Long> {
    fun save(project: ProjectJpaEntity): ProjectJpaEntity

    fun findById(id: Long): ProjectJpaEntity

    fun deleteAllByHost(userJpaEntity: UserJpaEntity)
}
