package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectReview

interface ProjectReviewQueryPort {
    fun readByReviewerId(reviewerId: Long): ProjectReview?

    fun findById(id: Long): ProjectReview?

    fun findAllByRevieweeUserId(revieweeUserId: Long): List<ProjectReview>
}
