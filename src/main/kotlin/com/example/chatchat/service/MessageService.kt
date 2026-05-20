package com.example.chatchat.service

import com.example.chatchat.domain.chat.Message
import com.example.chatchat.dto.chat.ChatMessageRequest
import com.example.chatchat.dto.chat.ChatMessageResponse
import com.example.chatchat.repository.ChatRoomRepository
import com.example.chatchat.repository.MessageRepository
import com.example.chatchat.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    private val chatRoomRepository: ChatRoomRepository
) {

    @Transactional
    fun saveMessage(request: ChatMessageRequest, loginId: String): ChatMessageResponse {
        val sender = userRepository.findByLoginId(loginId)
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다.")
        val chatRoom = chatRoomRepository.findById(request.roomId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 채팅방입니다.") }

        val message = Message(
            chatRoom = chatRoom,
            sender = sender,
            content = request.content,
            messageType = request.messageType
        )

        val savedMessage = messageRepository.save(message)
        return ChatMessageResponse.from(savedMessage)
    }

    @Transactional(readOnly = true)
    fun getMessages(roomId: Long, pageable: Pageable): Page<ChatMessageResponse> {
        // 검증: 요청한 유저가 해당 채팅방의 멤버인지 확인하는 로직 추가 가능
        return messageRepository.findByChatRoomIdOrderByIdDesc(roomId, pageable)
            .map { ChatMessageResponse.from(it) }
    }
}