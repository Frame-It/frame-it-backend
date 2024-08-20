package com.org.framelt.notification.domain

import com.org.framelt.user.domain.User
import java.time.LocalDateTime

class Notification(
    val sender: User,
    val receiver: User,
    val title: String,
    val content: String,
//    리다이렏트 보류
    val sendTime: LocalDateTime,
    val isRead: Boolean,
)
