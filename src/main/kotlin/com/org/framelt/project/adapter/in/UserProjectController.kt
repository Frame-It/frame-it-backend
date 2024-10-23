package com.org.framelt.project.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.project.adapter.`in`.response.UserProjectsResponse
import com.org.framelt.project.application.port.`in`.UserProjectUseCase
import com.org.framelt.project.common.ProjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProjectController(
    val userProjectUseCase: UserProjectUseCase,
) {
    @GetMapping("/users/projects")
    fun showProjectsOfUser(
        @RequestParam(required = false) status: String?,
        @RequestParam(defaultValue = "false") includesApplicant: Boolean,
        @Authorization userId: Long,
    ): ResponseEntity<UserProjectsResponse> {
        val command =
            ProjectMapper.toCommand(
                viewerId = userId,
                userId = userId,
                status = status,
                includesApplicant = includesApplicant,
            )
        val result = userProjectUseCase.readProjectsByUserId(command)
        val response = ProjectMapper.toResponse(result)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/{userId}/projects")
    fun showProjectsOfGuest(
        @PathVariable userId: Long,
        @Authorization viewerId: Long,
    ): ResponseEntity<UserProjectsResponse> {
        val command =
            ProjectMapper.toCommand(
                viewerId = viewerId,
                userId = userId,
                status = null,
                includesApplicant = false,
            )
        val result = userProjectUseCase.readProjectsByUserId(command)
        val response = ProjectMapper.toResponse(result)
        return ResponseEntity.ok(response)
    }
}
