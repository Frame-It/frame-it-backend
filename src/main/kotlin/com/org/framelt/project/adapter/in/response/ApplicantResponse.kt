package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.ApplicantModel
import java.time.LocalDate

data class ApplicantResponse(
    val applicantId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val appliedAt: LocalDate,
    val applyContent: String,
) {
    companion object {
        fun from(applicantModel: ApplicantModel) =
            ApplicantResponse(
                applicantId = applicantModel.applicantId,
                nickname = applicantModel.nickname,
                profileImageUrl = applicantModel.profileImageUrl,
                appliedAt = applicantModel.appliedAt,
                applyContent = applicantModel.applyContent,
            )
    }
}
