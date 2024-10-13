package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.project.domain.ProjectApplicantCancelReason
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "project_applicant")
@EntityListeners(AuditingEntityListener::class)
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
    val cancelContent: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    val cancelReason: ProjectApplicantCancelReason? = null,
    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime,
    @LastModifiedDate
    val modifiedAt: LocalDateTime,
) {
    companion object {
        fun fromDomain(projectApplicant: ProjectApplicant): ProjectApplicantJpaEntity =
            ProjectApplicantJpaEntity(
                id = projectApplicant.id,
                project = ProjectJpaEntity.fromDomain(projectApplicant.project),
                applicant = UserJpaEntity.fromDomain(projectApplicant.applicant),
                applyContent = projectApplicant.applyContent,
                isCanceled = projectApplicant.isCanceled,
                cancelContent = projectApplicant.cancelContent,
                cancelReason = projectApplicant.cancelReason,
                createdAt = projectApplicant.appliedAt,
                modifiedAt = projectApplicant.modifiedAt,
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
        cancelContent = cancelContent,
        appliedAt = createdAt,
        modifiedAt = modifiedAt,
    )
