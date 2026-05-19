package com.example.chatchat.service

import com.example.chatchat.domain.user.User
import com.example.chatchat.dto.auth.LoginRequest
import com.example.chatchat.dto.auth.SignupRequest
import com.example.chatchat.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signup(request: SignupRequest) {
        if (userRepository.findByLoginId(request.loginId) != null) {
            throw IllegalArgumentException("이미 사용 중인 아이디입니다.")
        }

        val user = User(
            loginId = request.loginId,
            password = passwordEncoder.encode(request.password), // 비밀번호 해시 처리
            nickname = request.nickname
        )
        userRepository.save(user)
    }

    fun login(request: LoginRequest): String {
        val user = userRepository.findByLoginId(request.loginId)
            ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")
        }

        // TODO: JWT 토큰 생성 및 반환 로직 구현
        return "로그인 성공 (JWT 토큰 발급 예정)"
    }
}