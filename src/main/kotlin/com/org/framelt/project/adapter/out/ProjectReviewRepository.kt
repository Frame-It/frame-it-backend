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
    override fun save(projectReview: ProjectReview) {
        val projectReviewJpaEntity = ProjectReviewJpaEntity.fromDomain(projectReview)
        projectReviewJpaRepository.save(projectReviewJpaEntity)
    }

    override fun readByReviewerIdAndRevieweeId(
        reviewerId: Long,
        revieweeId: Long,
    ): ProjectReview? =
        projectReviewJpaRepository
            .findByReviewerIdAndRevieweeId(
                reviewerId = reviewerId,
                revieweeId = revieweeId,
            )?.toDomain()
}
