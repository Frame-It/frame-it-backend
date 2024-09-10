package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.out.ProjectBookmarkCommandPort
import com.org.framelt.project.application.port.out.ProjectBookmarkQueryPort
import com.org.framelt.project.domain.ProjectBookmark
import com.org.framelt.user.adapter.out.UserJpaEntity
import org.springframework.stereotype.Repository

@Repository
class ProjectBookmarkRepository(
    val projectBookmarkJpaRepository: ProjectBookmarkJpaRepository,
) : ProjectBookmarkQueryPort,
    ProjectBookmarkCommandPort {
    override fun existsBookmark(
        projectId: Long,
        userId: Long,
    ): Boolean = projectBookmarkJpaRepository.existsByProjectIdAndUserId(projectId, userId)

    override fun bookmarkProject(projectBookmark: ProjectBookmark) {
        val projectBookmarkJpaEntity =
            ProjectBookmarkJpaEntity(
                project = ProjectJpaEntity.fromDomain(projectBookmark.project),
                user = UserJpaEntity.fromDomain(projectBookmark.user),
            )
        projectBookmarkJpaRepository.save(projectBookmarkJpaEntity)
    }

    override fun readByProjectIdAndUserId(
        projectId: Long,
        userId: Long,
    ): ProjectBookmark {
        val projectBookmarkJpaEntity =
            projectBookmarkJpaRepository.findByProjectIdAndUserId(projectId, userId)
        return projectBookmarkJpaEntity.toDomain()
    }

    override fun unbookmarkProject(projectBookmark: ProjectBookmark) {
        val projectBookmarkJpaEntity = ProjectBookmarkJpaEntity.fromDomain(projectBookmark)
        projectBookmarkJpaRepository.delete(projectBookmarkJpaEntity)
    }
}
