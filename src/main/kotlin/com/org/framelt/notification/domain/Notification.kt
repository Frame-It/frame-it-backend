package com.org.framelt.notification.domain

import com.org.framelt.notification.adapter.out.NotificationJpaEntity
import com.org.framelt.user.adapter.out.UserJpaEntity
import com.org.framelt.user.domain.User
import java.time.LocalDateTime

class Notification(
    val id: Long = 0L,
    val sender: User,
    val receiver: User,
    val title: String,
    val content: String,
    val sendTime: LocalDateTime,
    var isRead: Boolean,
) {
    fun markAsRead() {
        this.isRead = true
    }
}
