package com.org.framelt.user.application.port.out.persistence

import com.org.framelt.user.domain.User

interface UserCommandPort {
    fun save(user: User): User

    fun quit(
        user: User,
        quitReason: String?,
    )
}
