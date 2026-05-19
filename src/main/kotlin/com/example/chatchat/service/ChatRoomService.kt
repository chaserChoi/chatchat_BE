package com.example.chatchat.service

import com.example.chatchat.domain.chat.ChatRoom
import com.example.chatchat.dto.chat.ChatRoomCreateRequest
import com.example.chatchat.dto.chat.ChatRoomResponse
import com.example.chatchat.repository.ChatRoomRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun createRoom(request: ChatRoomCreateRequest): ChatRoomResponse {
        val chatRoom = ChatRoom(
            title = request.title,
            isGroupChat = request.isGroupChat,
            isSecret = request.isSecret,
            // 비밀방일 경우에만 비밀번호를 해시하여 저장
            password = if (request.isSecret && request.password != null) {
                passwordEncoder.encode(request.password)
            } else null
        )
        val savedRoom = chatRoomRepository.save(chatRoom)
        return ChatRoomResponse.from(savedRoom)
    }

    @Transactional(readOnly = true)
    fun getAllRooms(): List<ChatRoomResponse> {
        return chatRoomRepository.findAllByOrderByIdDesc()
            .map { ChatRoomResponse.from(it) }
    }
}