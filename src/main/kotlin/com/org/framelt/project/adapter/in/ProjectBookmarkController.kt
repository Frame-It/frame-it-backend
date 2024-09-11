package com.org.framelt.project.adapter.`in`

import com.org.framelt.project.adapter.`in`.response.BookmarkedProjectReadResponse
import com.org.framelt.project.application.port.`in`.BookmarkedProjectsReadCommand
import com.org.framelt.project.application.port.`in`.ProjectBookmarkCommand
import com.org.framelt.project.application.port.`in`.ProjectBookmarkUseCase
import com.org.framelt.project.application.port.`in`.ProjectUnbookmarkCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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

    @DeleteMapping("/projects/{projectId}/bookmarks")
    fun unbookmarkProject(
        @PathVariable projectId: Long,
        @RequestParam userId: Long,
    ): ResponseEntity<Unit> {
        val projectUnbookmarkCommand = ProjectUnbookmarkCommand(projectId, userId)
        projectBookmarkUseCase.unbookmarkProject(projectUnbookmarkCommand)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/projects/bookmarks")
    fun readBookmarkedProjects(
        @RequestParam userId: Long,
    ): ResponseEntity<List<BookmarkedProjectReadResponse>> {
        val bookmarkedProjectsReadCommand = BookmarkedProjectsReadCommand(userId)
        val bookmarkedProjects = projectBookmarkUseCase.readBookmarkedProjects(bookmarkedProjectsReadCommand)
        val response = bookmarkedProjects.map { BookmarkedProjectReadResponse.from(it) }
        return ResponseEntity.ok(response)
    }
}