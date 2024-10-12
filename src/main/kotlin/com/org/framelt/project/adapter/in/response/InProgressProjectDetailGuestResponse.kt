package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.InProgressProjectDetailModel
import java.time.LocalDateTime

data class InProgressProjectDetailGuestResponse(
    val title: String,
    val spot: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val host: InProgressProjectHostResponse,
) {
    companion object {
        fun from(inProgressProjectDetailModel: InProgressProjectDetailModel) =
            InProgressProjectDetailGuestResponse(
                title = inProgressProjectDetailModel.title,
                spot = inProgressProjectDetailModel.spot.name,
                shootingAt = inProgressProjectDetailModel.shootingAt,
                status = inProgressProjectDetailModel.status.name,
                host = InProgressProjectHostResponse.from(inProgressProjectDetailModel.host),
            )
    }
}
