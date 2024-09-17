package com.org.framelt.chat.domain

import com.org.framelt.user.domain.User

data class Chatting(
    val id: Long = 0L,
    val participant: List<User>,
    val messages: MutableList<Message> = mutableListOf(),
) {

    constructor(participant: List<User>) : this(0L, participant)

    fun addMessage(sender: User, content: String) {
        val message = Message(sender = sender, content = content)
        messages.add(message)
    }
}
