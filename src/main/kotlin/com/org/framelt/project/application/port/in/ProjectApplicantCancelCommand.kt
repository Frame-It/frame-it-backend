package com.org.framelt.project.application.port.`in`

data class ProjectApplicantCancelCommand(
    val projectId: Long,
    val applicantId: Long,
    val cancelReason: String,
)
