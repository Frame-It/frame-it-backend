package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.RecruitingProjectDetailGuestModel
import java.time.LocalDateTime

data class RecruitingProjectDetailGuestResponse(
    val title: String,
    val spot: String,
    val timeOption: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val myApplication: ApplicantResponse,
) {
    companion object {
        fun from(recruitingProjectDetailGuestModel: RecruitingProjectDetailGuestModel) =
            RecruitingProjectDetailGuestResponse(
                title = recruitingProjectDetailGuestModel.title,
                spot = recruitingProjectDetailGuestModel.spot.name,
                timeOption = recruitingProjectDetailGuestModel.timeOption.name,
                shootingAt = recruitingProjectDetailGuestModel.shootingAt,
                status = recruitingProjectDetailGuestModel.status.name,
                myApplication = ApplicantResponse.from(recruitingProjectDetailGuestModel.myApplication),
            )
    }
}
