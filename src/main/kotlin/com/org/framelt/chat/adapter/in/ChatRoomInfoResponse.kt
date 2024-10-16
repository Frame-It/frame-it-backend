package com.org.framelt.chat.adapter.`in`

data class ChatRoomInfoResponse(
    val chatId: Long,
    val participants: ChatRoomUserInfoResponse,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadMessageCount: Int,
)
