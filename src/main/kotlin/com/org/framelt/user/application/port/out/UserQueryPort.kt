package com.org.framelt.user.application.port.out

import com.org.framelt.user.domain.User

interface UserQueryPort {
    fun readById(id: Long): User
}
