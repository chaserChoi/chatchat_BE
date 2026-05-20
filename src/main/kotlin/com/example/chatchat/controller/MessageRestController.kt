package com.example.chatchat.controller

import com.example.chatchat.dto.chat.ChatMessageResponse
import com.example.chatchat.service.MessageService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rooms/{roomId}/messages")
class MessageRestController(
    private val messageService: MessageService
) {

    @GetMapping
    fun getMessages(
        @PathVariable roomId: Long,
        // 최신 메시지부터 가져오기 위해 정렬 기준을 ID 역순으로 설정
        @PageableDefault(size = 20, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<ChatMessageResponse>> {
        val messages = messageService.getMessages(roomId, pageable)
        return ResponseEntity.ok(messages)
    }
}