package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.InProgressProjectDetailModel
import java.time.LocalDateTime

data class InProgressProjectDetailResponse(
    val title: String,
    val spot: String,
    val timeOption: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val isHost: Boolean,
    val projectMember: InProgressProjectMemberResponse,
) {
    companion object {
        fun from(inProgressProjectDetailModel: InProgressProjectDetailModel) =
            InProgressProjectDetailResponse(
                title = inProgressProjectDetailModel.title,
                spot = inProgressProjectDetailModel.spot.name,
                timeOption = inProgressProjectDetailModel.timeOption.name,
                shootingAt = inProgressProjectDetailModel.shootingAt,
                status = inProgressProjectDetailModel.status.name,
                isHost = inProgressProjectDetailModel.isHost,
                projectMember = InProgressProjectMemberResponse.from(inProgressProjectDetailModel.projectMember),
            )
    }
}
