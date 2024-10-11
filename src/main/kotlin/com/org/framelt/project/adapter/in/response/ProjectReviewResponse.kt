package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.ProjectReviewModel

data class ProjectReviewResponse(
    val reviewerNickname: String,
    val tags: List<String>,
    val content: String,
) {
    companion object {
        fun from(projectReview: ProjectReviewModel): ProjectReviewResponse =
            ProjectReviewResponse(
                reviewerNickname = projectReview.reviewerNickname,
                tags = projectReview.tags.map { it.code },
                content = projectReview.content,
            )
    }
}
