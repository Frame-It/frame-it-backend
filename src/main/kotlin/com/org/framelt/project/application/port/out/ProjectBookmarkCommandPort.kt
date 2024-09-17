package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectBookmark

interface ProjectBookmarkCommandPort {
    fun bookmarkProject(projectBookmark: ProjectBookmark)

    fun unbookmarkProject(projectBookmark: ProjectBookmark)
}
