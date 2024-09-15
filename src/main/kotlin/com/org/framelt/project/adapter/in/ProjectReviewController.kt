package com.org.framelt.project.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.project.adapter.`in`.request.ProjectReviewRequest
import com.org.framelt.project.application.port.`in`.ProjectReviewCreateUseCase
import com.org.framelt.project.common.ProjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectReviewController(
    val projectReviewCreateUseCase: ProjectReviewCreateUseCase,
) {
    @PostMapping("/projects/{projectId}/reviews")
    fun review(
        @PathVariable projectId: Long,
        @Authorization reviewerId: Long,
        @RequestBody projectReviewRequest: ProjectReviewRequest,
    ): ResponseEntity<Unit> {
        val projectReviewCommand = ProjectMapper.toCommand(projectId, reviewerId, projectReviewRequest)
        projectReviewCreateUseCase.review(projectReviewCommand)
        return ResponseEntity.ok().build()
    }
}
