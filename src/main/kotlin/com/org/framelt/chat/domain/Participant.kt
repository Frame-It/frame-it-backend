package com.org.framelt.chat.domain

import com.org.framelt.user.domain.User

data class Participant(
    val user: User,
    var unreadCount: Int = 0,
)
