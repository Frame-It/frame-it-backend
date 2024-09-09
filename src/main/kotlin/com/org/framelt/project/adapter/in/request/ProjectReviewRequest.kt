package com.org.framelt.project.adapter.`in`.request

data class ProjectReviewRequest(
    val revieweeId: Long,
    val tags: List<String>,
    val content: String,
)
