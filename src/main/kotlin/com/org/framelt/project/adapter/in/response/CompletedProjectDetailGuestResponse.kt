package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.CompletedProjectDetailGuestModel
import java.time.LocalDateTime

data class CompletedProjectDetailGuestResponse(
    val title: String,
    val spot: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val isReviewDone: Boolean,
    val reviewId: Long?,
    val host: CompletedProjectHostResponse,
) {
    companion object {
        fun from(completedProjectDetailModel: CompletedProjectDetailGuestModel) =
            CompletedProjectDetailGuestResponse(
                title = completedProjectDetailModel.title,
                spot = completedProjectDetailModel.spot.name,
                shootingAt = completedProjectDetailModel.shootingAt,
                status = completedProjectDetailModel.status.name,
                isReviewDone = completedProjectDetailModel.isReviewDone,
                reviewId = completedProjectDetailModel.reviewId,
                host = CompletedProjectHostResponse.from(completedProjectDetailModel.host),
            )
    }
}
