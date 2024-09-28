package com.org.framelt.chat.adapter.`in`

data class ChatSummaryResponse(
    val chatId: Long,
    val participantName: String,
    val participantIdentity: String,
    val lastMessage: String,
    val unreadCount: Int
)