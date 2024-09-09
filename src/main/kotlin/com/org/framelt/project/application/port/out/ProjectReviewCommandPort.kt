package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectReview

interface ProjectReviewCommandPort {
    fun save(projectReview: ProjectReview)
}
