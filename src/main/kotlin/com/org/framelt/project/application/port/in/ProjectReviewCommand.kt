package com.org.framelt.project.application.port.`in`

data class ProjectReviewCommand(
    val projectId: Long,
    val reviewerId: Long,
    val revieweeId: Long,
    val tags: List<String>,
    val content: String,
)
