package com.org.framelt.project.application.port.`in`

interface ProjectBookmarkUseCase {
    fun bookmarkProject(projectBookmarkCommand: ProjectBookmarkCommand)

    fun unbookmarkProject(projectUnbookmarkCommand: ProjectUnbookmarkCommand)

    fun readBookmarkedProjects(bookmarkedProjectsReadCommand: BookmarkedProjectsReadCommand): List<BookmarkedProjectReadModel>
}
