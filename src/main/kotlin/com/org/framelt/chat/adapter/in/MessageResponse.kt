package com.org.framelt.chat.adapter.`in`

import java.time.LocalDateTime

data class MessageResponse(
    val messageId: Long,
    val sender: ChatUserInfoResponse,
    val timeStamp: LocalDateTime,
    val content: String,
    val isMe: Boolean,
)
