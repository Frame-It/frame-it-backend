package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.CompletedProjectDetailHostModel
import java.time.LocalDateTime

class CompletedProjectDetailHostResponse(
    val title: String,
    val spot: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val isReviewDone: Boolean,
    val reviewId: Long?,
    val guest: CompletedProjectGuestResponse,
) {
    companion object {
        fun from(projectDetail: CompletedProjectDetailHostModel): CompletedProjectDetailHostResponse =
            CompletedProjectDetailHostResponse(
                title = projectDetail.title,
                spot = projectDetail.spot.name,
                shootingAt = projectDetail.shootingAt,
                status = projectDetail.status.name,
                isReviewDone = projectDetail.isReviewDone,
                reviewId = projectDetail.reviewId,
                guest = CompletedProjectGuestResponse.from(projectDetail.guest),
            )
    }
}
