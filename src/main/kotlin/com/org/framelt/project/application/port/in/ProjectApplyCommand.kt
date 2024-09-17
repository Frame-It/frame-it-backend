package com.org.framelt.project.application.port.`in`

data class ProjectApplyCommand(
    val projectId: Long,
    val applicantId: Long,
    val applyContent: String,
)
