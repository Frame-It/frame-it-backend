package com.org.framelt.project.domain

import com.org.framelt.user.domain.User

class ProjectMember(
    val id: Long? = null,
    val member: User,
    val project: Project,
    val isHost: Boolean = false,
)
