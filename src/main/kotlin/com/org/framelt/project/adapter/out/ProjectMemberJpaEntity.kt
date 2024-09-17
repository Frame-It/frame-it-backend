package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.*

@Entity(name = "project_member")
class ProjectMemberJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "project_id")
    val project: ProjectJpaEntity,
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: UserJpaEntity,
    @Column(nullable = false)
    val isManager: Boolean = false,
    @Column(nullable = false)
    var hasCompletedProject: Boolean = false,
) {
    companion object {
        fun fromDomain(projectMember: ProjectMember): ProjectMemberJpaEntity =
            ProjectMemberJpaEntity(
                id = projectMember.id,
                project = ProjectJpaEntity.fromDomain(projectMember.project),
                member = UserJpaEntity.fromDomain(projectMember.member),
                isManager = projectMember.isHost,
                hasCompletedProject = projectMember.hasCompletedProject,
            )
    }
}

fun ProjectMemberJpaEntity.toDomain(): ProjectMember =
    ProjectMember(
        id = id!!,
        project = project.toDomain(),
        member = member.toDomain(),
        isHost = isManager,
        hasCompletedProject = hasCompletedProject,
    )
