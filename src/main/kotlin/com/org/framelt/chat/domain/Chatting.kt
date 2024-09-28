package com.org.framelt.chat.domain

import com.org.framelt.user.domain.User

data class Chatting(
    val id: Long = 0L,
    val participants: List<Participant>,
    val messages: MutableList<Message> = mutableListOf(),
) {

    constructor(participant: List<User>) : this(
        0L,
        participant.map { Participant(it) }.toMutableList(),
        mutableListOf()
    )

    fun addMessage(sender: User, content: String) {
        val message = Message(sender = sender, content = content)
        messages.add(message)
        for (participant in participants) {
            if (participant.user != sender) {
                participant.unreadCount += 1
            }
        }
    }

    fun markMessagesAsRead(user: User) {
        val participant = participants.find { it.user == user }
        participant?.unreadCount = 0
    }

    fun getUnreadCount(user: User): Int {
        return participants.find { it.user == user }?.unreadCount ?: 0
    }
}
