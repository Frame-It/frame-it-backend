package com.org.framelt.admin.view

import com.org.framelt.admin.application.AdminService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminViewController(
    private val adminService: AdminService,
) {
    @GetMapping("/admin")
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

    @GetMapping("/admin/login")
    fun loginPage(): String {
        return "login"
    }
}
