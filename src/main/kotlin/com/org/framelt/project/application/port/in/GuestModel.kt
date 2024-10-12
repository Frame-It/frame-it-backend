package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectApplicant
import java.time.LocalDate

data class GuestModel(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val appliedAt: LocalDate,
    val applyContent: String,
) {
    companion object {
        fun fromDomain(guest: ProjectApplicant): GuestModel =
            GuestModel(
                id = guest.applicant.id!!,
                nickname = guest.applicant.nickname,
                profileImageUrl = guest.applicant.profileImageUrl,
                appliedAt = guest.appliedAt.toLocalDate(),
                applyContent = guest.applyContent,
            )
    }
}
