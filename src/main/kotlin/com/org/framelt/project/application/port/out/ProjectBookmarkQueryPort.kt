package com.org.framelt.project.application.port.out

interface ProjectBookmarkQueryPort {
    fun existsBookmark(
        projectId: Long,
        userId: Long,
    ): Boolean
}
