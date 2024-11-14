package com.org.framelt.chat.application.port.out

data class CreateChatCommand(val userId: Long, val participantId: Long)
