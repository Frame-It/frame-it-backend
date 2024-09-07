package com.org.framelt.notification.adapter.`in`

import com.org.framelt.notification.application.port.`in`.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationDeleteUseCase: NotificationDeleteUseCase,
    private val notificationMarkAsReadUseCase: NotificationMarkAsReadUseCase,
    private val notificationQueryUseCase: NotificationQueryUseCase,
) {

    @PostMapping("/read-all")
    fun markAllAsRead(@RequestParam userId: Long): ResponseEntity<Void> {
        val command = MarkAllAsReadCommand(userId)
        notificationMarkAsReadUseCase.markAllAsRead(command)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{notificationId}/status")
    fun getNotificationStatus(@PathVariable notificationId: Long): ResponseEntity<NotificationStatusResponse> {
        val command = NotificationStatusCommand(notificationId)
        return ResponseEntity.ok(notificationQueryUseCase.getNotificationStatus(command))
    }

    @DeleteMapping("/{notificationId}")
    fun deleteNotification(@PathVariable notificationId: Long): ResponseEntity<Void> {
        val command = NotificationDeleteCommand(notificationId)
        notificationDeleteUseCase.deleteNotification(command)
        return ResponseEntity.noContent().build()
    }
}
