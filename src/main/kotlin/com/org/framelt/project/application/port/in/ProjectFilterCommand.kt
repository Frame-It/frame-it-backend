package com.org.framelt.project.application.port.`in`

import java.time.LocalDate

data class ProjectFilterCommand(
    val recruitmentRole: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val timeOption: String?,
    val spot: String?,
    val locationType: String?,
    val concepts: List<String>?,
    val userId: Long?,
)
