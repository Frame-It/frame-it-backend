package com.org.framelt.chat.domain

import com.org.framelt.user.domain.User

data class Chatting(
    val participant: List<User>,
) {
    var messages: List<Message> = listOf()
        private set
}
