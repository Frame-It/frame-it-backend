package com.org.framelt.project.application.port.`in`

data class ProjectCompleteCommand(
    val projectId: Long,
    val memberId: Long,
)
