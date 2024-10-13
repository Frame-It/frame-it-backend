package com.org.framelt.project.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.project.adapter.`in`.response.UserProjectsResponse
import com.org.framelt.project.application.port.`in`.UserProjectUseCase
import com.org.framelt.project.common.ProjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProjectController(
    val userProjectUseCase: UserProjectUseCase,
) {
    @GetMapping("/users/projects")
    fun showProejctsOfUser(
        @RequestParam(required = false) status: String?,
        @RequestParam(defaultValue = "false") includesApplicant: Boolean,
        @Authorization userId: Long,
    ): ResponseEntity<UserProjectsResponse> {
        val command = ProjectMapper.toCommand(userId, status, includesApplicant)
        val result = userProjectUseCase.readProjectsByUserId(command)
        val response = ProjectMapper.toResponse(result)
        return ResponseEntity.ok(response)
    }
}
