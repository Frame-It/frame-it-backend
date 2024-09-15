package com.org.framelt.notification.application.port.`in`

import com.org.framelt.notification.application.service.NotificationLetter

interface NotificationSendPort {
    fun sendTo(letter: NotificationLetter)
}
