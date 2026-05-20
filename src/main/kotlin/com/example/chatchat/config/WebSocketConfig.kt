package com.example.chatchat.config

import com.example.chatchat.util.JwtUtil
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(private val jwtUtil: JwtUtil) : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        // 메시지 발행(클라이언트 -> 서버) 요청 prefix
        config.setApplicationDestinationPrefixes("/pub")
        // 메시지 구독(서버 -> 클라이언트) 요청 prefix
        config.enableSimpleBroker("/sub", "/queue") // "/queue" 추가하여 1:N(채팅방) & 1:1(알림) 브로커 명확히 분리
        // 1:1 메시지(알림 등)를 위한 prefix
        config.setUserDestinationPrefix("/user")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // WebSocket 또는 SockJS 클라이언트가 연결을 시도할 엔드포인트
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*") // CORS 허용
//            .withSockJS()
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        // STOMP 연결 시 JWT 인증을 위한 인터셉터 등록
        registration.interceptors(StompAuthChannelInterceptor(jwtUtil))
    }

    /**
     * STOMP 연결 시 헤더의 JWT 토큰을 검증하는 인터셉터
     */
    inner class StompAuthChannelInterceptor(private val jwtUtil: JwtUtil) : ChannelInterceptor {
        override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
            val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)

            // STOMP 연결 요청일 때만 토큰 검증
            if (accessor != null && StompCommand.CONNECT == accessor.command) {
                val authHeader = accessor.getFirstNativeHeader("Authorization")
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    val token = authHeader.substring(7)
                    if (jwtUtil.validateToken(token)) {
                        val loginId = jwtUtil.getLoginIdFromToken(token)
                        // 검증 성공 시, Spring Security Principal 객체에 인증 정보 저장
                        val authentication = UsernamePasswordAuthenticationToken(loginId, null, emptyList())
                        accessor.user = authentication
                    }
                }
            }
            return message
        }
    }
}