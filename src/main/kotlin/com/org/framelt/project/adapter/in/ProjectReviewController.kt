package com.org.framelt.project.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.project.adapter.`in`.request.ProjectReviewRequest
import com.org.framelt.project.adapter.`in`.response.ProjectReviewResponse
import com.org.framelt.project.application.port.`in`.ProjectReviewCreateUseCase
import com.org.framelt.project.application.port.`in`.ProjectReviewReadUseCase
import com.org.framelt.project.application.port.`in`.UserProjectReviewReadCommand
import com.org.framelt.project.common.ProjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectReviewController(
    val projectReviewCreateUseCase: ProjectReviewCreateUseCase,
    val projectReviewReadUseCase: ProjectReviewReadUseCase,
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

    @GetMapping("/projects/reviews/{reviewId}")
    fun getProjectReview(
        @PathVariable reviewId: Long,
        @Authorization userId: Long,
    ): ResponseEntity<ProjectReviewResponse> {
        val command = ProjectMapper.toCommand(reviewId, userId)
        val projectReview = projectReviewReadUseCase.getProjectReview(command)
        val response = ProjectReviewResponse.from(projectReview)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/{userId}/projects/reviews")
    fun getProjectReviewsOfUser(
        @PathVariable userId: Long,
        @Authorization requestUserId: Long,
    ): ResponseEntity<List<ProjectReviewResponse>> {
        val command = UserProjectReviewReadCommand(userId, requestUserId)
        val projectReviews = projectReviewReadUseCase.getProjectReviewsOfUser(command)
        val response = projectReviews.map { ProjectReviewResponse.from(it) }
        return ResponseEntity.ok(response)
    }
}
