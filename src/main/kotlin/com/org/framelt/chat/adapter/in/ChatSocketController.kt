package com.org.framelt.chat.adapter.`in`

import com.org.framelt.chat.application.port.`in`.ChatUseCase
import com.org.framelt.config.auth.Authorization
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller

@Controller
class ChatSocketController(
    private val chatUseCase: ChatUseCase,
    private val messagingTemplate: SimpMessagingTemplate,
) {

    @SubscribeMapping("/chat/{chatRoomId}")
    fun subscribeChat(@Authorization userId: Long, @DestinationVariable chatRoomId: Long): ChattingResponse {
        return chatUseCase.getChat(userId, chatRoomId)
    }

    @MessageMapping("/chat/{chatRoomId}")
    fun sendMessage(
        request: SendMessageRequest,
        @DestinationVariable chatRoomId: Long,
    ) {
        val command = ChatMapper.toSendMessageCommand(request.receiverId, chatRoomId, request)
        chatUseCase.sendMessage(command)

        val response = ChatMessageResponse(request.receiverId, request.content)
        messagingTemplate.convertAndSend("/subscribe/chat/$chatRoomId", response)
    }
}
