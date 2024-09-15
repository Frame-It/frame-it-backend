package com.org.framelt.notification.adapter.`in`

import java.time.LocalDateTime

data class NotificationResponse(
    val id: Long,
    val title : String,
    val content: String,
    val sendTime: LocalDateTime,
    val isRead: Boolean
)
