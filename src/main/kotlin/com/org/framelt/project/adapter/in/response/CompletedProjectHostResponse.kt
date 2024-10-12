package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.CompletedProjectHostModel

data class CompletedProjectHostResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun from(completedProjectHostModel: CompletedProjectHostModel) =
            CompletedProjectHostResponse(
                id = completedProjectHostModel.id,
                nickname = completedProjectHostModel.nickname,
                profileImageUrl = completedProjectHostModel.profileImageUrl,
                isReviewDone = completedProjectHostModel.isReviewDone,
                reviewId = completedProjectHostModel.reviewId,
            )
    }
}
