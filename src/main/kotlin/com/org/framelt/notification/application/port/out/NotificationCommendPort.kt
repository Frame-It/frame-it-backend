package com.org.framelt.notification.application.port.out

interface NotificationCommendPort {
    fun deleteById(notificationId: Long)
    fun updateAll(userId: Long)
    fun updateById(id: Long)
}
