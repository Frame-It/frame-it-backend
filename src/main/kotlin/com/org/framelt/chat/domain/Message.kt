package com.org.framelt.chat.domain

import com.org.framelt.user.domain.User
import java.time.LocalDateTime

class Message(
    val sender: User,
    val timeScript: LocalDateTime,
    val content: String,
)
