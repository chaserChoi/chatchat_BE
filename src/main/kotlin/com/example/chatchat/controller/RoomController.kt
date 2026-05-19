package com.example.chatchat.controller

import com.example.chatchat.dto.chat.ChatRoomCreateRequest
import com.example.chatchat.dto.chat.ChatRoomResponse
import com.example.chatchat.service.ChatRoomService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rooms")
class RoomController(
    private val chatRoomService: ChatRoomService
) {

    @PostMapping
    fun createRoom(@RequestBody request: ChatRoomCreateRequest): ResponseEntity<ChatRoomResponse> {
        val room = chatRoomService.createRoom(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(room)
    }

    @GetMapping
    fun getRooms(): ResponseEntity<List<ChatRoomResponse>> {
        val rooms = chatRoomService.getAllRooms()
        return ResponseEntity.ok(rooms)
    }
}