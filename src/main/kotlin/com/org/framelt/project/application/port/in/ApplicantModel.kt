package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectApplicant
import java.time.LocalDate

data class ApplicantModel(
    val applicantId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val appliedAt: LocalDate,
    val applyContent: String,
) {
    companion object {
        fun fromDomain(projectApplicant: ProjectApplicant) =
            ApplicantModel(
                applicantId = projectApplicant.applicant.id!!,
                nickname = projectApplicant.applicant.nickname,
                profileImageUrl = projectApplicant.applicant.profileImageUrl,
                // TODO: audiitng 데이터 추가되면 수정
                // appliedAt = projectApplicant.createdAt,
                appliedAt = LocalDate.now(),
                applyContent = projectApplicant.applyContent,
            )
    }
}
