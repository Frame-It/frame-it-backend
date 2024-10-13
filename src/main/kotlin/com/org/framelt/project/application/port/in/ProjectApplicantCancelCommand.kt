package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectApplicantCancelReason

data class ProjectApplicantCancelCommand(
    val projectId: Long,
    val applicantId: Long,
    val cancelReason: ProjectApplicantCancelReason,
    val content: String,
)
