package com.example.chatchat.controller

import com.example.chatchat.dto.auth.LoginRequest
import com.example.chatchat.dto.auth.SignupRequest
import com.example.chatchat.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<String> {
        authService.signup(request)
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.")
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        val token = authService.login(request)
        // TODO: JWT 토큰을 헤더나 응답 바디에 담아 반환
        return ResponseEntity.ok(token)
    }
}