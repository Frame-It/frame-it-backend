package com.org.framelt.project.adapter.out

import org.springframework.data.repository.Repository

interface ProjectReviewJpaRepository : Repository<ProjectReviewJpaEntity, Long> {
    fun save(projectReviewJpaEntity: ProjectReviewJpaEntity): ProjectReviewJpaEntity

    fun findByReviewerId(reviewerId: Long): ProjectReviewJpaEntity?

    fun findById(id: Long): ProjectReviewJpaEntity?

    fun findAllByRevieweeMemberId(revieweeId: Long): List<ProjectReviewJpaEntity>
}
