package com.org.framelt.user.application.service

import com.org.framelt.user.application.port.`in`.SignUpCommand
import com.org.framelt.user.application.port.`in`.SignUpUseCase
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.Identity
import org.springframework.stereotype.Service

@Service
class UserService(
    val userQueryPort: UserQueryPort,
    val userCommandPort: UserCommandPort,
) : SignUpUseCase {
    override fun signUp(signUpCommand: SignUpCommand) {
        val user = userQueryPort.readById(signUpCommand.id)
        user.fillProfile(
            name = signUpCommand.name,
            birthDate = signUpCommand.birthDate,
            nickname = signUpCommand.nickname,
            notificationsEnabled = signUpCommand.notificationsEnabled,
            identity = Identity.of(signUpCommand.identity),
        )
        userCommandPort.save(user)
    }
}
