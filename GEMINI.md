# 프로젝트 개요 (Project Overview)

- **프로젝트명:** CHATCHAT (공식 아님)
- **소개:** 카카오톡 등 간단한 온라인 웹 채팅 서비스
- **주 개발자:** 최재혁
- **목표:** 웹과 모바일을 모두 지원하는 반응형 실시간 채팅 서비스 구축

## 핵심 기능 (Core Features)

1. **사용자 인증:** 간단한 아이디 & 비밀번호 및 JWT 기반 로그인
2. **채팅방 관리:** 로그인 후 채팅방 목록 조회 및 원하는 채팅방 입장
3. **채팅 기능:** 일대일 및 단체 채팅 지원
4. **비밀 채팅:** 채팅방 입장 시 비밀번호 입력 필수
5. **파일 전송:** 이미지 및 파일 전송 기능
6. **실시간 알림:** 새로운 메시지 수신 시 실시간 알림 제공
7. **반응형 UI:** 웹/모바일 환경을 모두 고려한 반응형 웹 디자인

---

# 기술 스택 (Tech Stack)

## Front-end

- **Core:** React(TypeScript), React Router
- **Styling:** Tailwind CSS (반응형 웹 구현)
- **State Management (Client/Local):** Zustand (채팅방 상태, 소켓 연결 상태, UI 토글)
- **State Management (Server):** TanStack Query (채팅방 목록, 유저 정보 등 REST API 데이터 캐싱)
- **Network & API:** Axios
- **Real-time Communication:** StompJS & SockJS-client
- **UI Components:** React-Toastify (앱 내 실시간 메시지 알림 UI)

## Back-end

- **Core:** Spring Boot 4.0.6 (Kotlin)
- **Database Access:** Spring Data JPA, QueryDSL (복잡한 쿼리 및 동적 쿼리 처리)
- **Security:** Spring Security, JWT (JSON Web Token)
- **Real-time Communication:** Spring WebSocket & STOMP (채팅 메시지 및 알림 Pub/Sub)
- **Message Broker / Cache:** Redis (세션 관리 및 채팅 메시지 브로커 역할)
- **File Handling:** Spring Standard MultipartFile (로컬 스토리지 기반 이미지/파일 처리)

## Database

- **RDBMS:** MySQL (or MariaDB)

---

# AI 어시스턴트 코딩 가이드라인 (AI Assistant Guidelines)

Gemini Code Assist 및 CLI는 코드 제안 및 자동 완성 시 아래의 가이드라인을 엄격하게 준수합니다.

## 1. 전반적인 코딩 스타일

- 주 개발자는 과거 React와 Java/Spring Boot를 활용한 풀스택 개발 경험이 있습니다. 본 프로젝트에서는 백엔드를 Kotlin으로 구축하므로, Java 스타일을 직역한 코드가 아닌 **Kotlin의 관용적인 문법(Idiomatic Kotlin)**과 널 안전성(Null Safety)을 극대화한 코드를 제안해야 합니다.
- 모든 컴포넌트와 클래스는 단일 책임 원칙(SRP)을 준수하여 분리합니다.

## 2. Front-end (React) 구현 규칙

- **상태 관리의 분리:**
  - REST API 통신(목록 조회, 과거 채팅 내역 등)은 반드시 `TanStack Query`를 사용하여 서버 상태를 캐싱합니다.
  - WebSocket을 통해 실시간으로 들어오는 메시지 배열 관리와 전역 UI 상태는 `Zustand`를 사용합니다.
- **스타일링:** 커스텀 CSS 작성은 지양하고 `Tailwind CSS`의 유틸리티 클래스를 우선적으로 사용하여 반응형(Mobile-first) 디자인을 구성합니다.
- **알림 구현:** STOMP 채널을 통해 새로운 메시지 알림 이벤트를 수신하면 `React-Toastify`를 호출하여 화면에 렌더링합니다.

## 3. Back-end (Kotlin + Spring Boot) 구현 규칙

- **REST API & 소켓 분리:** 일반적인 데이터 CUD 작업과 파일 업로드는 RESTful API로 구성하고, 채팅 메시지 전송 및 실시간 알림은 WebSocket(STOMP)으로 분리하여 처리합니다.
- **인증 및 보안:** JWT 토큰은 HTTP 헤더(Authorization)를 통해 검증하며, WebSocket 연결 시에도 Interceptor를 통해 JWT 유효성을 우선 검증해야 합니다.
- **데이터베이스 연동:** 엔티티(Entity) 설계 시 지연 로딩(Lazy Loading)을 기본으로 사용하고, N+1 문제가 발생하는 조회 로직은 `QueryDSL`을 활용하여 최적화된 쿼리를 작성합니다.
- **비밀 채팅방 검증:** 채팅방 입장 API 호출 시 해당 방이 비밀방일 경우, 서비스 레이어에서 비밀번호 일치 여부를 반드시 검증한 뒤 입장 권한(또는 입장 토큰)을 발급합니다.