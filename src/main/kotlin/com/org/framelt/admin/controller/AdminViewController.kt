package com.org.framelt.admin.controller

import com.org.framelt.admin.view.UserAdminService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminViewController(
    private val userAdminService: UserAdminService,
) {
    @GetMapping("/admin")
    fun userList(model: Model): String {
        val users = userAdminService.readAllUsers()
        model.addAttribute("users", users)
        return "userList"
    }
}
