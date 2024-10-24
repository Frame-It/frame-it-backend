package com.org.framelt.chat.adapter.`in`

data class ChattingResponse(
    val chatId: Long,
    val participants: List<ChatUserInfoResponse>,
    val messages: List<MessageResponse>,
    val isQuit: Boolean
)
