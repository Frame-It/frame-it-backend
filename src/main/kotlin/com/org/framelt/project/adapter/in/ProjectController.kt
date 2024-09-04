package com.org.framelt.project.adapter.`in`

import com.org.framelt.project.adapter.`in`.request.ProjectCreateRequest
import com.org.framelt.project.adapter.`in`.response.ProjectCreateResponse
import com.org.framelt.project.application.port.`in`.ProjectCreateUseCase
import com.org.framelt.project.common.ProjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(
    val projectCreateUseCase: ProjectCreateUseCase,
) {
    @PostMapping("/projects")
    fun create(
        @RequestBody projectCreateRequest: ProjectCreateRequest,
    ): ResponseEntity<ProjectCreateResponse> {
        val createCommand = ProjectMapper.toCommand(projectCreateRequest)
        val createdProjectId = projectCreateUseCase.create(createCommand)
        val response =
            ProjectCreateResponse(
                projectId = createdProjectId,
                title = projectCreateRequest.title,
            )
        return ResponseEntity.ok(response)
    }
}
