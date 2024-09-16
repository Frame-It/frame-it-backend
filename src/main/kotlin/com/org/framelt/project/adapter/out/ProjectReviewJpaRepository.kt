package com.org.framelt.project.adapter.out

import org.springframework.data.repository.Repository

interface ProjectReviewJpaRepository : Repository<ProjectReviewJpaEntity, Long> {
    fun save(projectReviewJpaEntity: ProjectReviewJpaEntity)

    fun findByReviewerIdAndRevieweeId(
        reviewerId: Long,
        revieweeId: Long,
    ): ProjectReviewJpaEntity?

    fun findById(id: Long): ProjectReviewJpaEntity?
}
