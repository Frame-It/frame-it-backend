package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectConcept
import java.time.LocalDate

data class ProjectFilterCommand(
    val recruitmentRole: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val timeOption: String?,
    val spot: String?,
    val locationType: String?,
    val concepts: List<ProjectConcept>?,
    val userId: Long,
)
