package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectReview

interface ProjectReviewQueryPort {
    fun readByReviewerIdAndRevieweeId(
        reviewerId: Long,
        revieweeId: Long,
    ): ProjectReview?

    abstract fun findById(id: Long): ProjectReview?
}
