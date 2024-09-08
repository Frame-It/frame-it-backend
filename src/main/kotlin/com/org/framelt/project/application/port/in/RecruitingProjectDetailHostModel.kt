package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectApplicant
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.Status
import com.org.framelt.project.domain.TimeOption
import java.time.LocalDateTime

data class RecruitingProjectDetailHostModel(
    val title: String,
    val spot: Spot,
    val timeOption: TimeOption,
    val shootingAt: LocalDateTime,
    val status: Status,
    val applicants: List<ApplicantModel>,
) {
    companion object {
        fun fromDomain(
            project: Project,
            applicants: List<ProjectApplicant>,
        ) = run {
            RecruitingProjectDetailHostModel(
                title = project.title,
                spot = project.spot,
                timeOption = project.timeOption,
                shootingAt = project.shootingAt,
                status = project.status,
                applicants =
                    applicants.map {
                        ApplicantModel.fromDomain(it)
                    },
            )
        }
    }
}
