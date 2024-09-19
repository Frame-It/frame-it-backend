package com.org.framelt.notification.adapter.`in`

import com.org.framelt.config.auth.Authorization
import com.org.framelt.notification.application.port.`in`.NotificationDeleteUseCase
import com.org.framelt.notification.application.port.`in`.NotificationMarkAsReadUseCase
import com.org.framelt.notification.application.port.`in`.NotificationQueryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationDeleteUseCase: NotificationDeleteUseCase,
    private val notificationMarkAsReadUseCase: NotificationMarkAsReadUseCase,
    private val notificationQueryUseCase: NotificationQueryUseCase,
) {

    @PostMapping("/readAll")
    fun markAllAsRead(@Authorization userId: Long): ResponseEntity<Void> {
        val command = MarkAllAsReadCommand(userId)
        notificationMarkAsReadUseCase.markAllAsRead(command)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/read")
    fun findNotifications(@Authorization userId: Long): ResponseEntity<List<NotificationResponse>> {
        val command = NotificationReadAllCommand(userId)
        return ResponseEntity.ok(notificationQueryUseCase.getNotificationStatus(command))
    }

    @GetMapping("/{id}")
    fun findNotification(
        @Authorization userId: Long,
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        val command = NotificationReadCommand(userId, id)
        notificationQueryUseCase.changeNotificationStatus(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteNotification(@Authorization userId: Long, @PathVariable id: Long): ResponseEntity<Void> {
        val command = NotificationDeleteCommand(userId, id)
        notificationDeleteUseCase.deleteNotification(command)
        return ResponseEntity.noContent().build()
    }
}
