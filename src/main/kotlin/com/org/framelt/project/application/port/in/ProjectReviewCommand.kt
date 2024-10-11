package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectReviewTag

data class ProjectReviewCommand(
    val projectId: Long,
    val reviewerId: Long,
    val revieweeId: Long,
    val tags: List<ProjectReviewTag>,
    val content: String,
)
