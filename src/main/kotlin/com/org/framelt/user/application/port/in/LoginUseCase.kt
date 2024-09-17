package com.org.framelt.user.application.port.`in`

interface LoginUseCase {
    fun login(loginCommand: LoginCommand): String
}
