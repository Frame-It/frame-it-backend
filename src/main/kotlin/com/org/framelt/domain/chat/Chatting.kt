package com.org.framelt.domain.chat

import com.org.framelt.domain.user.User

data class Chatting(
    val participant: List<User>,

    ) {
    var messages: List<Message> = listOf()
        private set
}
