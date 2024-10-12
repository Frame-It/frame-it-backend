package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.ProjectReview
import java.time.LocalDate

data class CompletedProjectGuestModel(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val appliedAt: LocalDate,
    val applyContent: String,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun fromDomain(
            guest: ProjectMember,
            guestProjectReview: ProjectReview?,
            applicantOfGuest: ProjectApplicant,
        ): CompletedProjectGuestModel =
            CompletedProjectGuestModel(
                id = guest.member.id!!,
                nickname = guest.member.nickname,
                profileImageUrl = guest.member.profileImageUrl,
                appliedAt = applicantOfGuest.appliedAt.toLocalDate(),
                applyContent = applicantOfGuest.applyContent,
                isReviewDone = guestProjectReview != null,
                reviewId = guestProjectReview?.id,
            )
    }
}
