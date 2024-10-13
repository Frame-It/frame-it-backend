package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.ProjectReview

data class CompletedProjectHostModel(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun fromDomain(
            host: ProjectMember,
            hostProjectReview: ProjectReview?,
        ) = CompletedProjectHostModel(
            id = host.member.id!!,
            nickname = host.member.nickname,
            profileImageUrl = host.member.profileImageUrl,
            isReviewDone = hostProjectReview != null,
            reviewId = hostProjectReview?.id,
        )
    }
}
