package com.org.framelt.chat.domain

import com.org.framelt.user.domain.User
import java.time.LocalDateTime

data class Participant(
    val id: Long = 0L,
    val user: User,
    var unreadCount: Int = 0,
    var lastMessageTime: LocalDateTime? = null, // New field to store the last message timestamp

) {
    constructor(user: User) : this(0, user, 0)

    fun readMessages() {
        unreadCount == 0
    }

    fun addUnreadCount() {
        unreadCount++
    }

    fun updateLastMessageTime(time: LocalDateTime) {
        lastMessageTime = time
    }
}