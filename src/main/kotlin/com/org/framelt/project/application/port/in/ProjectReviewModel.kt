package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectReview

data class ProjectReviewModel(
    val reviewerNickname: String,
    val tags: List<String>,
    val content: String,
) {
    companion object {
        fun fromDomain(projectReview: ProjectReview): ProjectReviewModel =
            ProjectReviewModel(
                reviewerNickname = projectReview.reviewer.member.nickname,
                tags = projectReview.tags,
                content = projectReview.content,
            )
    }
}