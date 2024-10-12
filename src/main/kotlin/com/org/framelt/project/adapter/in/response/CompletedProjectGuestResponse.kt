package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.CompletedProjectGuestModel
import java.time.LocalDate

data class CompletedProjectGuestResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val appliedAt: LocalDate,
    val applyContent: String,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun from(guest: CompletedProjectGuestModel): CompletedProjectGuestResponse =
            CompletedProjectGuestResponse(
                id = guest.id,
                nickname = guest.nickname,
                profileImageUrl = guest.profileImageUrl,
                appliedAt = guest.appliedAt,
                applyContent = guest.applyContent,
                isReviewDone = guest.isReviewDone,
                reviewId = guest.reviewId,
            )
    }
}
