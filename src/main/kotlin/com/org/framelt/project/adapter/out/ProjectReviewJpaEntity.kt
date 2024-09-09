package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.ProjectReview
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "project_review")
class ProjectReviewJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    val reviewer: ProjectMemberJpaEntity,
    @ManyToOne
    @JoinColumn(name = "reviewee_id")
    val reviewee: ProjectMemberJpaEntity,
    @ElementCollection
    @CollectionTable(
        name = "project_review_feedback_tag",
        joinColumns = [JoinColumn(name = "project_review_id")],
    )
    val tags: List<String>,
    @Column(nullable = false)
    val content: String,
) {
    companion object {
        fun fromDomain(projectReview: ProjectReview): ProjectReviewJpaEntity =
            ProjectReviewJpaEntity(
                id = projectReview.id,
                reviewer = ProjectMemberJpaEntity.fromDomain(projectReview.reviewer),
                reviewee = ProjectMemberJpaEntity.fromDomain(projectReview.reviewee),
                tags = projectReview.tags,
                content = projectReview.content,
            )
    }
}

fun ProjectReviewJpaEntity.toDomain(): ProjectReview =
    ProjectReview(
        id = id,
        reviewer = reviewer.toDomain(),
        reviewee = reviewee.toDomain(),
        tags = tags,
        content = content,
    )
