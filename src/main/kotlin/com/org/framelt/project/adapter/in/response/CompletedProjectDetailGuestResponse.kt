package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.CompletedProjectDetailGuestModel
import java.time.LocalDateTime

data class CompletedProjectDetailGuestResponse(
    val title: String,
    val spot: String,
    val timeOption: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val isReviewDone: Boolean,
    val reviewId: Long?,
    val isHost: Boolean,
    val projectMember: CompletedProjectMemberResponse,
) {
    companion object {
        fun from(completedProjectDetailModel: CompletedProjectDetailGuestModel) =
            CompletedProjectDetailGuestResponse(
                title = completedProjectDetailModel.title,
                spot = completedProjectDetailModel.spot.name,
                timeOption = completedProjectDetailModel.timeOption.name,
                shootingAt = completedProjectDetailModel.shootingAt,
                status = completedProjectDetailModel.status.name,
                isReviewDone = completedProjectDetailModel.isReviewDone,
                reviewId = completedProjectDetailModel.reviewId,
                isHost = completedProjectDetailModel.isHost,
                projectMember = CompletedProjectMemberResponse.from(completedProjectDetailModel.projectMember),
            )
    }
}
