package com.org.framelt.domain.chat

import com.org.framelt.domain.user.User
import java.time.LocalDateTime

class Message(
    val sender: User,
    val timeScript: LocalDateTime,
    val content: String
) {
}
