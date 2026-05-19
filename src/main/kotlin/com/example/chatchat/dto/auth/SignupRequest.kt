package com.example.chatchat.dto.auth

data class SignupRequest(
    val loginId: String,
    val password: String,
    val nickname: String
)