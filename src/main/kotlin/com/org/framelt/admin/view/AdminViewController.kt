package com.org.framelt.admin.view

import com.org.framelt.admin.application.AdminService
import com.org.framelt.admin.view.response.ProjectAdminResponse
import com.org.framelt.admin.view.response.ProjectHostResponse
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminViewController(
    private val adminService: AdminService,
) {
    @GetMapping("/admin")
    fun homePage(
        model: Model,
        httpSession: HttpSession,
    ): String {
        val isAdmin = httpSession.getAttribute("isAdmin") as? Boolean ?: false
        if (!isAdmin) {
            return "redirect:/admin/login"
        }
        return "home"
    }

    @GetMapping("/admin/login")
    fun loginPage(): String {
        return "login"
    }

    @GetMapping("/admin/users")
    fun userListPage(
        model: Model,
        httpSession: HttpSession,
    ): String {
        val isAdmin = httpSession.getAttribute("isAdmin") as? Boolean ?: false
        if (!isAdmin) {
            return "redirect:/admin/login"
        }
        val users = adminService.readAllUsers()
        model.addAttribute("users", users)
        return "userList"
    }

    @GetMapping("/admin/projects")
    fun projectListPage(
        model: Model,
        httpSession: HttpSession,
    ): String {
        val isAdmin = httpSession.getAttribute("isAdmin") as? Boolean ?: false
        if (!isAdmin) {
            return "redirect:/admin/login"
        }
        val projects = adminService.readAllProjects()
        model.addAttribute("projects", projects)
        return "projectList"
    }
}
