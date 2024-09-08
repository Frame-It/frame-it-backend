package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.user.adapter.out.UserJpaEntity
import com.org.framelt.user.adapter.out.toDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLRestriction

@Entity(name = "project_applicant")
@SQLRestriction("is_canceled = false")
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
    @Column(nullable = false)
    val isCanceled: Boolean = false,
    @Column(nullable = true)
    val cancelReason: String? = null,
) {
    companion object {
        fun fromDomain(projectApplicant: ProjectApplicant): ProjectApplicantJpaEntity =
            ProjectApplicantJpaEntity(
                id = projectApplicant.id,
                project = ProjectJpaEntity.fromDomain(projectApplicant.project),
                applicant = UserJpaEntity.fromDomain(projectApplicant.applicant),
                applyContent = projectApplicant.applyContent,
                isCanceled = projectApplicant.isCanceled,
                cancelReason = projectApplicant.cancelReason,
            )
    }
}

fun ProjectApplicantJpaEntity.toDomain(): ProjectApplicant =
    ProjectApplicant(
        id = id,
        project = project.toDomain(),
        applicant = applicant.toDomain(),
        applyContent = applyContent,
        isCanceled = isCanceled,
        cancelReason = cancelReason,
    )
