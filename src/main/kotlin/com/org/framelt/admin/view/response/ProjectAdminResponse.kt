package com.org.framelt.admin.view.response

data class ProjectAdminResponse(
    val id: Long,
    val name: String,
    val status: String,
    val host: ProjectHostResponse,
    val recruitmentRole: String,
)

data class ProjectHostResponse(
    val nickname: String,
    val identity: String,
)
