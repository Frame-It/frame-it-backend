package com.org.framelt.project.adapter.`in`

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
    ): ResponseEntity<RecruitingProjectDetailHostResponse> {
        val result = projectReadUseCase.getRecruitingProjectForHost(projectId)
        val response = RecruitingProjectDetailHostResponse.from(result)
        return ResponseEntity.ok(response)
    }
}
