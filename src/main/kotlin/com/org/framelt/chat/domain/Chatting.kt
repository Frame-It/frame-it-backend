package com.org.framelt.chat.domain

import com.org.framelt.user.domain.User

data class Chatting(
    val id: Long = 0L,
    val participants: List<Participant>,
    val messages: MutableList<Message> = mutableListOf(),
) {
    constructor(participants: List<Participant>) : this(0L, participants)

    fun addMessage(sender: User, content: String) {
        val message = Message(sender = sender, content = content)
        messages.add(message)
        participants.find { it.user.id == sender.id }?.updateLastMessageTime(message.timeScript)
    }

    fun updateUnreadCount(userId: Long) {
        participants.filter { it.user.id == userId }.map { it.readMessages() }
    }

    fun increaseUnreadCount(receiverId: Long) {
        participants.filter { it.user.id == receiverId }.map { it.addUnreadCount() }

    }
}
