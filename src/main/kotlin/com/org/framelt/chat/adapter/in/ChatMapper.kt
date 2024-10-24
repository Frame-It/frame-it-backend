package com.org.framelt.chat.adapter.`in`

import CreateChatCommand
import SendMessageCommand
import com.org.framelt.chat.domain.Chatting
import com.org.framelt.user.domain.User

class ChatMapper {
    companion object {
        fun toCreateCommand(userId: Long, request: CreateChatRequest): CreateChatCommand {
            return CreateChatCommand(userId, request.participantId)
        }

        fun toSendMessageCommand(userId: Long, chatId: Long, command: SendMessageRequest): SendMessageCommand {
            return SendMessageCommand(userId, chatId,command.receiverId, command.content)
        }

        fun toResponse(sender: User, chat: Chatting): ChattingResponse {
            val participantResponses = chat.participants
                .filter { it.user.id != sender.id }
                .map { participant ->
                    ChatUserInfoResponse(
                        id = participant.user.id ?: 0L,
                        name = participant.user.name,
                        profileImageUrl = participant.user.profileImageUrl ?: ""
                    )
                }
            val isQuit = chat.participants
                .filter { it.user.id != sender.id }
                .any { it.user.isQuit }


            val messageResponses = chat.messages.map { message ->
                MessageResponse(
                    messageId = message.id,
                    sender = ChatUserInfoResponse(
                        id = message.sender.id ?: 0L,
                        name = message.sender.name,
                        profileImageUrl = message.sender.profileImageUrl ?: ""
                    ),
                    timeStamp = message.timeScript,
                    content = message.content,
                    isMe = message.sender.id == sender.id
                )
            }.sortedWith(compareBy<MessageResponse> { !it.isMe }.thenBy { it.timeStamp })

            return ChattingResponse(
                chatId = chat.id,
                participants = participantResponses,
                messages = messageResponses,
                isQuit = isQuit
            )
        }
    }
}
