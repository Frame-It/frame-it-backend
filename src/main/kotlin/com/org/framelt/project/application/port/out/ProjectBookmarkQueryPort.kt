package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectBookmark

interface ProjectBookmarkQueryPort {
    fun existsBookmark(
        projectId: Long,
        userId: Long,
    ): Boolean

    fun readByProjectIdAndUserId(
        projectId: Long,
        userId: Long,
    ): ProjectBookmark
}
