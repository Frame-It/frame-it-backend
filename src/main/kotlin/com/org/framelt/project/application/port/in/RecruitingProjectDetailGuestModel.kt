package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.Status
import com.org.framelt.project.domain.TimeOption
import java.time.LocalDateTime

data class RecruitingProjectDetailGuestModel(
    val title: String,
    val spot: Spot,
    val timeOption: TimeOption,
    val shootingAt: LocalDateTime,
    val hostId: Long,
    val status: Status,
    val myApplication: ApplicantModel,
) {
    companion object {
        fun fromDomain(
            project: Project,
            applicant: ProjectApplicant,
        ) = run {
            RecruitingProjectDetailGuestModel(
                title = project.title,
                spot = project.spot,
                timeOption = project.timeOption,
                shootingAt = project.shootingAt,
                hostId = project.host.id!!,
                status = project.status,
                myApplication = ApplicantModel.fromDomain(applicant),
            )
        }
    }
}
