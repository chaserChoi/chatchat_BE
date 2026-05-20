package com.example.chatchat.dto.chat

import com.example.chatchat.domain.chat.MessageType

data class ChatMessageRequest(
    val roomId: Long,
    val content: String,
    val messageType: MessageType = MessageType.TEXT
)
