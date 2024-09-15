package com.org.framelt.notification.adapter.`in`

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
    fun markAllAsRead(@AuthenticationPrincipal userId: Long): ResponseEntity<Void> {
        val command = MarkAllAsReadCommand(userId)
        notificationMarkAsReadUseCase.markAllAsRead(command)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{notificationId}/status")
    fun findNotifications(@AuthenticationPrincipal userId: Long): ResponseEntity<List<NotificationResponse>> {
        val command = NotificationReadCommand(userId)
        return ResponseEntity.ok(notificationQueryUseCase.getNotificationStatus(command))
    }

    @DeleteMapping("/{notificationId}")
    fun deleteNotification(@AuthenticationPrincipal userId: Long, @RequestBody request: NotificationDeleteRequest): ResponseEntity<Void> {
        val command = NotificationDeleteCommand(userId, request.id)
        notificationDeleteUseCase.deleteNotification(command)
        return ResponseEntity.noContent().build()
    }
}
