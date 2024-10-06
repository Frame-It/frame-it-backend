package com.org.framelt.project.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.project.adapter.`in`.response.UserProjectItemResponse
import com.org.framelt.project.application.port.`in`.UserProjectReadCommand
import com.org.framelt.project.application.port.`in`.UserProjectUseCase
import com.org.framelt.project.common.ProjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProjectController(
    val userProjectUseCase: UserProjectUseCase,
) {
    @GetMapping("/users/{userId}/projects")
    fun showProejctsOfUser(
        @PathVariable userId: Long,
        @Authorization requestUserId: Long,
    ): ResponseEntity<List<UserProjectItemResponse>> {
        val command = UserProjectReadCommand(userId, requestUserId)
        val result = userProjectUseCase.readProjectsByUserId(command)
        val response = ProjectMapper.toResponse(result)
        return ResponseEntity.ok(response)
    }
}
