package com.org.framelt.admin.view

import com.org.framelt.admin.application.AdminService
import com.org.framelt.admin.view.request.AdminLoginRequest
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminApiController(
    private val adminService: AdminService,
) {
    @PostMapping("/admin/login")
    fun login(
        @ModelAttribute request: AdminLoginRequest,
        httpSession: HttpSession,
    ): ResponseEntity<Unit> {
        adminService.login(request.id, request.password)
        httpSession.setAttribute("isAdmin", true)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/admin/projects/{projectId}")
    fun deleteProject(
        @PathVariable projectId: Long,
        httpSession: HttpSession,
    ) {
        adminService.deleteProject(projectId, httpSession)
    }
}
