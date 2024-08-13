package com.org.framelt.domain

import com.org.framelt.domain.user.User
import java.time.LocalDateTime

class Notification(
    val sender: User,
    val receiver: User,
    val title: String,
    val content: String,
//    리다이렏트 보류
    val sendTime: LocalDateTime,
    val isRead: Boolean,
) {
}