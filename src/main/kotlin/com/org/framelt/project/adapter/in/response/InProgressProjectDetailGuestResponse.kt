package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.InProgressProjectDetailModel
import java.time.LocalDateTime

data class InProgressProjectDetailGuestResponse(
    val title: String,
    val address: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val host: InProgressProjectHostResponse,
    val isReviewDone: Boolean,
    val reviewId: Long?,
) {
    companion object {
        fun from(inProgressProjectDetailModel: InProgressProjectDetailModel) =
            InProgressProjectDetailGuestResponse(
                title = inProgressProjectDetailModel.title,
                address = inProgressProjectDetailModel.address,
                shootingAt = inProgressProjectDetailModel.shootingAt,
                status = inProgressProjectDetailModel.status.name,
                host = InProgressProjectHostResponse.from(inProgressProjectDetailModel.host),
                isReviewDone = inProgressProjectDetailModel.isReviewDone,
                reviewId = inProgressProjectDetailModel.reviewId,
            )
    }
}
