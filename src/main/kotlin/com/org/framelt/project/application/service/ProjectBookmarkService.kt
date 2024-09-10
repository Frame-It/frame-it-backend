package com.org.framelt.project.application.service

import com.org.framelt.project.application.port.`in`.ProjectBookmarkCommand
import com.org.framelt.project.application.port.`in`.ProjectBookmarkUseCase
import com.org.framelt.project.application.port.`in`.ProjectUnbookmarkCommand
import com.org.framelt.project.application.port.out.ProjectBookmarkCommandPort
import com.org.framelt.project.application.port.out.ProjectBookmarkQueryPort
import com.org.framelt.project.application.port.out.ProjectQueryPort
import com.org.framelt.project.domain.ProjectBookmark
import com.org.framelt.user.application.port.out.UserQueryPort
import org.springframework.stereotype.Service

@Service
class ProjectBookmarkService(
    val projectBookmarkCommandPort: ProjectBookmarkCommandPort,
    val projectBookmarkQueryPort: ProjectBookmarkQueryPort,
    val projectQueryPort: ProjectQueryPort,
    val userQueryPort: UserQueryPort,
) : ProjectBookmarkUseCase {
    override fun bookmarkProject(projectBookmarkCommand: ProjectBookmarkCommand) {
        validateBookmarkExistence(projectBookmarkCommand)

        val project = projectQueryPort.readById(projectBookmarkCommand.projectId)
        val user = userQueryPort.readById(projectBookmarkCommand.userId)
        val projectBookmark =
            ProjectBookmark(
                project = project,
                user = user,
            )
        projectBookmarkCommandPort.bookmarkProject(projectBookmark)
    }

    private fun validateBookmarkExistence(projectBookmarkCommand: ProjectBookmarkCommand) {
        if (projectBookmarkQueryPort.existsBookmark(
                projectBookmarkCommand.projectId,
                projectBookmarkCommand.userId,
            )
        ) {
            throw IllegalArgumentException("해당 사용자와 프로젝트에 대한 북마크가 이미 존재합니다.")
        }
    }

    override fun unbookmarkProject(projectUnbookmarkCommand: ProjectUnbookmarkCommand) {
        val projectBookmark =
            projectBookmarkQueryPort.readByProjectIdAndUserId(
                projectUnbookmarkCommand.projectId,
                projectUnbookmarkCommand.userId,
            )
        projectBookmarkCommandPort.unbookmarkProject(projectBookmark)
    }
}
