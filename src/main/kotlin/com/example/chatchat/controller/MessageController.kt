package com.example.chatchat.controller

import com.example.chatchat.dto.chat.ChatMessageRequest
import com.example.chatchat.repository.ChatRoomMemberRepository
import com.example.chatchat.service.MessageService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class MessageController(
    private val messageService: MessageService,
    private val messagingTemplate: SimpMessagingTemplate,
    private val chatRoomMemberRepository: ChatRoomMemberRepository
) {

    @MessageMapping("/chat/message")
    fun sendMessage(@Payload request: ChatMessageRequest, principal: Principal) {
        // 1. 메시지를 DB에 저장 (principal.name은 JWT 토큰에서 추출한 loginId)
        val messageResponse = messageService.saveMessage(request, principal.name)

        // 2. 채팅방 구독자들에게 메시지 브로드캐스팅
        messagingTemplate.convertAndSend("/sub/chat/room/${request.roomId}", messageResponse)

        // 3. 채팅방에 없는 다른 멤버들에게 알림 전송
        val roomMembers = chatRoomMemberRepository.findAllByChatRoomId(request.roomId)
        roomMembers.forEach { member ->
            // 메시지 보낸 사람은 제외
            if (member.user.loginId != principal.name) {
                // /user/{loginId}/sub/notify 경로로 알림 전송
                messagingTemplate.convertAndSendToUser(
                    member.user.loginId,
                    "/queue/notify",
                    messageResponse // 실제로는 더 가벼운 알림용 DTO 사용하는 것이 좋음
                )
            }
        }
    }
}