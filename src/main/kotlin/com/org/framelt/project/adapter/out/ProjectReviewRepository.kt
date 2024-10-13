package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.out.ProjectReviewCommandPort
import com.org.framelt.project.application.port.out.ProjectReviewQueryPort
import com.org.framelt.project.domain.ProjectReview
import org.springframework.stereotype.Repository

@Repository
class ProjectReviewRepository(
    private val projectReviewJpaRepository: ProjectReviewJpaRepository,
) : ProjectReviewCommandPort,
    ProjectReviewQueryPort {
    override fun save(projectReview: ProjectReview): ProjectReview {
        val projectReviewJpaEntity = ProjectReviewJpaEntity.fromDomain(projectReview)
        return projectReviewJpaRepository.save(projectReviewJpaEntity).toDomain()
    }

    override fun readByReviewerId(reviewerId: Long): ProjectReview? =
        projectReviewJpaRepository
            .findByReviewerId(reviewerId)
            ?.toDomain()

    override fun findById(id: Long): ProjectReview? = projectReviewJpaRepository.findById(id)?.toDomain()

    override fun findAllByRevieweeUserId(revieweeUserId: Long): List<ProjectReview> =
        projectReviewJpaRepository.findAllByRevieweeMemberId(revieweeUserId).map {
            it.toDomain()
        }
}
