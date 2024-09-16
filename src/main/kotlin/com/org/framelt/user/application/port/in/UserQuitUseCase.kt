package com.org.framelt.user.application.port.`in`

interface UserQuitUseCase {
    fun quit(userQuitCommand: UserQuitCommand)

    fun hasInProgressProjects(userId: Long): Boolean
}
