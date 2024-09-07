package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.user.adapter.out.UserJpaEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "project_applicant")
class ProjectApplicantJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "project_id")
    val project: ProjectJpaEntity,
    @ManyToOne
    @JoinColumn(name = "applicant_id")
    val applicant: UserJpaEntity,
    @Column(nullable = false)
    val applyContent: String,
) {
    companion object {
        fun fromDomain(projectApplicant: ProjectApplicant): ProjectApplicantJpaEntity =
            ProjectApplicantJpaEntity(
                project = ProjectJpaEntity.fromDomain(projectApplicant.project),
                applicant = UserJpaEntity.fromDomain(projectApplicant.applicant),
                applyContent = projectApplicant.applyContent,
            )
    }
}
