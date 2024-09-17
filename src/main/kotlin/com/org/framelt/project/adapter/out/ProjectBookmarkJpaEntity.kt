package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.ProjectBookmark
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.*

@Entity(name = "project_bookmark")
class ProjectBookmarkJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "project_id")
    val project: ProjectJpaEntity,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserJpaEntity,
) {
    companion object {
        fun fromDomain(projectBookmark: ProjectBookmark): ProjectBookmarkJpaEntity =
            ProjectBookmarkJpaEntity(
                id = projectBookmark.id,
                project = ProjectJpaEntity.fromDomain(projectBookmark.project),
                user = UserJpaEntity.fromDomain(projectBookmark.user),
            )
    }
}

fun ProjectBookmarkJpaEntity.toDomain(): ProjectBookmark =
    ProjectBookmark(
        id = id,
        project = project.toDomain(),
        user = user.toDomain(),
    )
