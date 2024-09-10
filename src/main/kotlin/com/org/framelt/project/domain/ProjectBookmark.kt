package com.org.framelt.project.domain

import com.org.framelt.user.domain.User

class ProjectBookmark(
    val id: Long? = null,
    val project: Project,
    val user: User,
)
