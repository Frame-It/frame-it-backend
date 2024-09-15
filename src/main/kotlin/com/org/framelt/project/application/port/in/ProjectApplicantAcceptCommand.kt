package com.org.framelt.project.application.port.`in`

data class ProjectApplicantAcceptCommand(
    val projectId: Long,
    val userId: Long,
    val applicantId: Long,
)
