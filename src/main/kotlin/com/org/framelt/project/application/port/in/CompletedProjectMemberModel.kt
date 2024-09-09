package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.ProjectReview

data class CompletedProjectMemberModel(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun fromDomain(
            projectMember: ProjectMember,
            projectReview: ProjectReview?,
        ) = CompletedProjectMemberModel(
            id = projectMember.member.id!!,
            nickname = projectMember.member.nickname,
            profileImageUrl = projectMember.member.profileImageUrl,
            isReviewDone = projectReview != null,
            reviewId = projectReview?.id,
        )
    }
}
