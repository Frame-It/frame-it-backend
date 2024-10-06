package com.org.framelt.project.application.port.`in`

interface UserProjectUseCase {
    fun readProjectsByUserId(userProjectReadCommand: UserProjectReadCommand): List<UserProjectModel>
}
