package com.org.framelt.chat.application.service

import com.org.framelt.chat.adapter.out.ChatJpaEntity
import com.org.framelt.chat.domain.Chatting
import com.org.framelt.user.domain.User

interface ChatCommendPort {
    fun save(chat: Chatting): Chatting?
    fun update(sender: User, chat: Chatting): Chatting?
    fun findChatBetweenUsers(firstUserId: Long, secondUserId: Long): ChatJpaEntity?
}
