package com.org.framelt.project.adapter.`in`

import com.org.framelt.project.application.port.`in`.ProjectBookmarkCommand
import com.org.framelt.project.application.port.`in`.ProjectBookmarkUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectBookmarkController(
    val projectBookmarkUseCase: ProjectBookmarkUseCase,
) {
    @PostMapping("/projects/{projectId}/bookmarks")
    fun bookmarkProject(
        @PathVariable projectId: Long,
        @RequestParam userId: Long,
    ): ResponseEntity<Unit> {
        val projectBookmarkCommand = ProjectBookmarkCommand(projectId, userId)
        projectBookmarkUseCase.bookmarkProject(projectBookmarkCommand)
        return ResponseEntity.ok().build()
    }
}
