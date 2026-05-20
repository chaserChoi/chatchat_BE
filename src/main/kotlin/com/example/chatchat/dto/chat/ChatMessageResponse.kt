package com.example.chatchat.dto.chat

import com.example.chatchat.domain.chat.Message
import com.example.chatchat.domain.chat.MessageType
import java.time.LocalDateTime

data class ChatMessageResponse(
    val messageId: Long,
    val roomId: Long,
    val senderId: Long,
    val senderNickname: String,
    val content: String?,
    val messageType: MessageType,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(message: Message): ChatMessageResponse {
            return ChatMessageResponse(
                messageId = message.id,
                roomId = message.chatRoom.id,
                senderId = message.sender.id,
                senderNickname = message.sender.nickname,
                content = message.content,
                messageType = message.messageType,
                createdAt = message.createdAt
            )
        }
    }
}
