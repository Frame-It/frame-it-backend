package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Status

data class ProjectReviewResult(
    val id: Long,
    val projectStatus: Status,
)
