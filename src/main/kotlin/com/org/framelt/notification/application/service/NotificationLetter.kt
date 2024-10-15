package com.org.framelt.notification.application.service

import com.org.framelt.user.domain.User

data class NotificationLetter(
    val sender: User,
    val receiver: User,
    val title: String,
    val content: String,
)
