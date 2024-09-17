package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.CompletedProjectMemberModel

data class CompletedProjectMemberResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun from(completedProjectMemberModel: CompletedProjectMemberModel) =
            CompletedProjectMemberResponse(
                id = completedProjectMemberModel.id,
                nickname = completedProjectMemberModel.nickname,
                profileImageUrl = completedProjectMemberModel.profileImageUrl,
                isReviewDone = completedProjectMemberModel.isReviewDone,
                reviewId = completedProjectMemberModel.reviewId,
            )
    }
}
