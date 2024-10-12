package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.InProgressProjectDetailHostModel
import java.time.LocalDateTime

data class InProgressProjectDetailHostResponse(
    val title: String,
    val spot: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val guest: GuestResponse,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun from(model: InProgressProjectDetailHostModel): InProgressProjectDetailHostResponse =
            InProgressProjectDetailHostResponse(
                title = model.title,
                spot = model.spot.name,
                shootingAt = model.shootingAt,
                status = model.status.name,
                guest = GuestResponse.from(model.guest),
                isReviewDone = model.isReviewDone,
                reviewId = model.reviewId,
            )
    }
}
