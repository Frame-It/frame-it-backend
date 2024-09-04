package com.org.framelt.project.adapter.`in`

import com.org.framelt.project.adapter.`in`.request.ProjectCreateRequest
import com.org.framelt.project.adapter.`in`.request.ProjectUpdateRequest
import com.org.framelt.project.adapter.`in`.response.ProjectCreateResponse
import com.org.framelt.project.adapter.`in`.response.ProjectDetailResponse
import com.org.framelt.project.adapter.`in`.response.ProjectUpdateResponse
import com.org.framelt.project.application.port.`in`.ProjectCreateUseCase
import com.org.framelt.project.application.port.`in`.ProjectReadUseCase
import com.org.framelt.project.application.port.`in`.ProjectUpdateUseCase
import com.org.framelt.project.common.ProjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(
    val projectCreateUseCase: ProjectCreateUseCase,
    val projectReadUseCase: ProjectReadUseCase,
    val projectUpdateUseCase: ProjectUpdateUseCase,
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

    @GetMapping("/projects/{projectId}")
    fun showDetail(
        @PathVariable projectId: Long,
    ): ResponseEntity<ProjectDetailResponse> {
        val projectDetail = projectReadUseCase.getProjectDetail(projectId)
        val response = ProjectMapper.toResponse(projectDetail)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/projects")
    fun update(
        @RequestBody projectUpdateRequest: ProjectUpdateRequest,
    ): ResponseEntity<ProjectUpdateResponse> {
        val updateCommand = ProjectMapper.toCommand(projectUpdateRequest)
        val updatedProjectId = projectUpdateUseCase.update(updateCommand)
        val response = ProjectUpdateResponse(projectId = updatedProjectId)
        return ResponseEntity.ok(response)
    }
}
