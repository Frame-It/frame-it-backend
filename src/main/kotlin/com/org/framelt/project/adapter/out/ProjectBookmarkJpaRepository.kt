package com.org.framelt.project.adapter.out

import org.springframework.data.repository.Repository

interface ProjectBookmarkJpaRepository : Repository<ProjectBookmarkJpaEntity, Long> {
    fun save(projectBookmark: ProjectBookmarkJpaEntity)

    fun existsByProjectIdAndUserId(
        projectId: Long,
        userId: Long,
    ): Boolean

    fun findByProjectIdAndUserId(
        projectId: Long,
        userId: Long,
    ): ProjectBookmarkJpaEntity

    fun delete(projectBookmarkJpaEntity: ProjectBookmarkJpaEntity)
}
