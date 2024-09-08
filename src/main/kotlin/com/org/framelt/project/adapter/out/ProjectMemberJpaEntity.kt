package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.user.adapter.out.UserJpaEntity
import com.org.framelt.user.adapter.out.toDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

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
) {
    companion object {
        fun fromDomain(projectMember: ProjectMember): ProjectMemberJpaEntity =
            ProjectMemberJpaEntity(
                project = ProjectJpaEntity.fromDomain(projectMember.project),
                member = UserJpaEntity.fromDomain(projectMember.member),
                isManager = projectMember.isHost,
            )
    }
}

fun ProjectMemberJpaEntity.toDomain(): ProjectMember =
    ProjectMember(
        id = id!!,
        project = project.toDomain(),
        member = member.toDomain(),
        isHost = isManager,
    )
