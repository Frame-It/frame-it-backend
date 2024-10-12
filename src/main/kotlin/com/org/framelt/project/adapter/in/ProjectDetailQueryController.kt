package com.org.framelt.project.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.project.adapter.`in`.response.CompletedProjectDetailGuestResponse
import com.org.framelt.project.adapter.`in`.response.CompletedProjectDetailHostResponse
import com.org.framelt.project.adapter.`in`.response.InProgressProjectDetailGuestResponse
import com.org.framelt.project.adapter.`in`.response.InProgressProjectDetailHostResponse
import com.org.framelt.project.adapter.`in`.response.RecruitingProjectDetailGuestResponse
import com.org.framelt.project.adapter.`in`.response.RecruitingProjectDetailHostResponse
import com.org.framelt.project.application.port.`in`.ProjectReadUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectDetailQueryController(
    private val projectReadUseCase: ProjectReadUseCase,
) {
    @GetMapping("/recruiting-projects/{projectId}/host")
    fun showRecruitingProjectForHost(
        @PathVariable projectId: Long,
        @Authorization userId: Long,
    ): ResponseEntity<RecruitingProjectDetailHostResponse> {
        val result = projectReadUseCase.getRecruitingProjectForHost(projectId, userId)
        val response = RecruitingProjectDetailHostResponse.from(result)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/recruiting-projects/{projectId}/guest")
    fun showRecruitingProjectForGuest(
        @PathVariable projectId: Long,
        @Authorization userId: Long,
    ): ResponseEntity<RecruitingProjectDetailGuestResponse> {
        val result = projectReadUseCase.getRecruitingProjectForGuest(projectId, userId)
        val response = RecruitingProjectDetailGuestResponse.from(result)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/in-progress-projects/{projectId}/host")
    fun showInProgressProjectForHost(
        @PathVariable projectId: Long,
        @Authorization userId: Long,
    ): ResponseEntity<InProgressProjectDetailHostResponse> {
        val result = projectReadUseCase.getInProgressProjectForHost(projectId, userId)
        val response = InProgressProjectDetailHostResponse.from(result)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/in-progress-projects/{projectId}/guest")
    fun showInProgressProjectForGuest(
        @PathVariable projectId: Long,
        @Authorization userId: Long,
    ): ResponseEntity<InProgressProjectDetailGuestResponse> {
        val result = projectReadUseCase.getInProgressProjectForGuest(projectId, userId)
        val response = InProgressProjectDetailGuestResponse.from(result)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/completed-projects/{projectId}/host")
    fun showCompletedProjectForHost(
        @PathVariable projectId: Long,
        @Authorization userId: Long,
    ): ResponseEntity<CompletedProjectDetailHostResponse> {
        val result = projectReadUseCase.getCompletedProjectForHost(projectId, userId)
        val response = CompletedProjectDetailHostResponse.from(result)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/completed-projects/{projectId}/guest")
    fun showCompletedProjectForGuest(
        @PathVariable projectId: Long,
        @Authorization userId: Long,
    ): ResponseEntity<CompletedProjectDetailGuestResponse> {
        val result = projectReadUseCase.getCompletedProjectForGuest(projectId, userId)
        val response = CompletedProjectDetailGuestResponse.from(result)
        return ResponseEntity.ok(response)
    }
}
