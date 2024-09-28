package com.org.framelt.chat.application.service

import com.org.framelt.chat.domain.Chatting

fun ChatReadPort.getById(id: Long): Chatting{
    return findById(id) ?: throw IllegalArgumentException("존제하지 않은 채팅방 입니다.")
}

interface ChatReadPort {
    fun findById(id: Long): Chatting?
    fun findByUserId(userId: Long): List<Chatting>
}