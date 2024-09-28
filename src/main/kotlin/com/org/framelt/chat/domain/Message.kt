package com.org.framelt.chat.domain

import com.org.framelt.user.domain.User
import java.time.LocalDateTime

data class Message(
    val id:Long= 0L,
    val sender: User,
    val timeScript: LocalDateTime = LocalDateTime.now(),
    val content: String,
)
