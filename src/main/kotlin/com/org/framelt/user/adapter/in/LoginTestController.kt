package com.org.framelt.user.adapter.`in`

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginTestController {
    @GetMapping("/login")
    fun loginPage(): String = "login.html"
}
