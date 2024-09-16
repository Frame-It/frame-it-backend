package com.org.framelt.user.application.service

import com.org.framelt.user.application.port.`in`.SignUpCommand
import com.org.framelt.user.application.port.`in`.SignUpUseCase
import com.org.framelt.user.application.port.`in`.UserAccountInfoModel
import com.org.framelt.user.application.port.`in`.UserAccountReadUseCase
import com.org.framelt.user.application.port.`in`.UserNicknameCheckCommand
import com.org.framelt.user.application.port.`in`.UserNicknameCheckUseCase
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.Identity
import org.springframework.stereotype.Service

@Service
class UserService(
    val userQueryPort: UserQueryPort,
    val userCommandPort: UserCommandPort,
) : SignUpUseCase,
    UserAccountReadUseCase,
    UserNicknameCheckUseCase {
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

    override fun getAccountInfo(userId: Long): UserAccountInfoModel {
        val user = userQueryPort.readById(userId)
        return UserAccountInfoModel(
            name = user.name,
            nickname = user.nickname,
            email = user.email,
            notificationsEnabled = user.notificationsEnabled,
        )
    }

    override fun isNicknameDuplicated(userNicknameCheckCommand: UserNicknameCheckCommand): Boolean =
        userQueryPort.existsByNickname(userNicknameCheckCommand.nickname)
}
