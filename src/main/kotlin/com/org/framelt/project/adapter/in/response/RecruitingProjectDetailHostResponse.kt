package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.RecruitingProjectDetailHostModel
import java.time.LocalDateTime

data class RecruitingProjectDetailHostResponse(
    val title: String,
    val address: String,
    val timeOption: String,
    val shootingAt: LocalDateTime,
    val status: String,
    val applicants: List<ApplicantResponse>,
) {
    companion object {
        fun from(recruitingProjectDetailHostModel: RecruitingProjectDetailHostModel) =
            RecruitingProjectDetailHostResponse(
                title = recruitingProjectDetailHostModel.title,
                address = recruitingProjectDetailHostModel.address,
                timeOption = recruitingProjectDetailHostModel.timeOption.name,
                shootingAt = recruitingProjectDetailHostModel.shootingAt,
                status = recruitingProjectDetailHostModel.status.name,
                applicants =
                    recruitingProjectDetailHostModel.applicants.map {
                        ApplicantResponse.from(it)
                    },
            )
    }
}
