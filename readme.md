
# 📚 BookBookClub
**BookBookClub**은 책에 대한 생각과 감상을 자유롭게 공유할 수 있는 SNS 플랫폼입니다.  
간단한 UI와 외부 도서 API 연동을 통해 독서 경험을 나누고, 커뮤니티를 형성할 수 있습니다.



<br>

----

<br>

## 📆 개발 기간 
- 2025.04.16 ~ ing.

<br>

----

<br>

## ✨ 관심사 및 목표
- **JPA 성능 최적화**에 관심이 많습니다. N+1 문제, 지연/즉시 로딩, fetch join 등을 학습하고 프로젝트에 적용하고 있습니다.
- **계층 분리 및 도메인 기반 구조**에 익숙해지고 있으며, 유지보수성과 확장성을 고려한 구조를 지향합니다.
- **API 응답 표준화, 전역 예외 처리, 테스트 가능한 구조 설계**에 관심이 많습니다.
- 실무에서는 경험하지 못한 기술들을 **사이드 프로젝트**를 통해 적극적으로 도입하고 있습니다.  
  예: OAuth2, Redis, Kafka, Docker, CI/CD 등
- 장기적으로는 사용자에게 가치 있는 서비스를 만드는 **문제 해결 중심의 백엔드 개발자**가 되고 싶습니다.

<br>

----

<br>

## 💻✨ 주요 기능 구현 현황

| 구분 | 구현 기능 |
|------|----------|
| **회원 가입 및 인증** | 이메일 인증 기반 회원가입<br>Redis와 DB 동시 저장<br>이메일 인증 상태 확인 및 가입 제한 |
| **로그인 / 인증** | JWT 기반 로그인<br>Access/Refresh Token 관리 (Redis 활용) |
| **소셜 로그인** | Google, Naver 연동<br>Provider별 서비스 분리<br>닉네임 중복 시 예외 처리 |
| **회원 정보 관리** | 회원 조회 API (`/me`)<br>닉네임 중복 확인 API<br>프로필 이미지 업로드 및 저장 (로컬, 향후 S3 예정) |
| **예외 처리 및 공통 응답 포맷** | `@RestControllerAdvice` 기반 글로벌 예외 처리<br>`ApiResponse<T>` 통일된 응답 포맷 적용 |
| **보안** | URL 접근 제어 설정<br>비밀번호 암호화(BCrypt) |
| **기타** | 이메일 인증 내역 테이블 관리<br>로그인 이력/재가입 제어 로직 구현 예정 |


<br>

----

<br>

## 🔧 사용 기술
## Backend
- Java 17
- Spring Boot 3
- Spring Security (JWT, OAuth2)
- JPA (Hibernate), QueryDSL
- Redis (Token 저장소 및 인증용도)
- MySQL

### Frontend
- Thymeleaf (기초 UI 구성)
- JavaScript (ES6)

### DevOps
- Gradle
- Git (Git Flow 예정)
- Docker / Kubernetes (추후 도입 예정)

<br>

----

<br>

## 📁 프로젝트 구조
~~~
src/main/java/ddururi/bookbookclub/
├── domain/              # 도메인별 핵심 로직 (예: user, post 등 추가 예정)
└── global/
    ├── common/         # 공통 응답 포맷, 상수 등
    ├── config/         # 전역 설정 클래스
    ├── exception/      # 커스텀 예외, 예외 핸들러
    ├── jwt/            # JWT 관련 유틸 및 필터
    └── security/       # 스프링 시큐리티 설정 및 필터

~~~


<br>

----

<br>

## 📕 개발일지
** 링크를 클릭하면 개발일지를 작성한 블로그로 이동 됩니다.

### 회원 (로그인/회원가입/로그아웃)
- [#000 프로젝트 생성 (프로젝트 생성, MySQL 연결, 개발 편의 설정, 패키지 설정 등)](https://ddururiiiiiii.tistory.com/598)
- [#001 회원 도메인(User) 개발](https://ddururiiiiiii.tistory.com/604)
- [#002 회원(User) 도메인 단위 테스트](https://ddururiiiiiii.tistory.com/605)
- [#003 회원(User) 도메인 회원가입 API 구현 + 테스트](https://ddururiiiiiii.tistory.com/608)
- [#004 회원 정보 수정 API 구현](https://ddururiiiiiii.tistory.com/610)
- [#005 로그인 구현 (Feat.JWT 기반 인증)](https://ddururiiiiiii.tistory.com/611)
- [#006 회원가입 전, 이메일 인증 구현](https://ddururiiiiiii.tistory.com/613)
- [#007 로그인 구현2 (refreshToken 도입) / 로그아웃](https://ddururiiiiiii.tistory.com/614)
- [#008 로그인 구현3 (refreshToken 재발급, AccessToken 블랙리스트 기능)](https://ddururiiiiiii.tistory.com/615)
- [#009 회원탈퇴 구현 (Spring Scheduler)](https://ddururiiiiiii.tistory.com/616)
- [#010 회원가입 수정 (6개월 이내 재가입 불가능)](https://ddururiiiiiii.tistory.com/617)
- [#011 Oauth 로그인 구현 (구글)](https://ddururiiiiiii.tistory.com/618)
- [#012 Oauth 로그인 구현 (네이버)](https://ddururiiiiiii.tistory.com/619)
- [#013 회원 프로필 사진 등록 기능 구현](https://ddururiiiiiii.tistory.com/620)
- [#014 글로벌 예외처리 및 API 응답 포맷 통일](https://ddururiiiiiii.tistory.com/621)
- [#015 이메일 인증 실패 시도 횟수 제한 기능](https://ddururiiiiiii.tistory.com/623)

### 책(Book)
- [#016 북(Book) 도메인 개발 및 단위 테스트](https://ddururiiiiiii.tistory.com/637)
- [#017 북(Book) 도메인 API 구현 및 테스트](https://ddururiiiiiii.tistory.com/639)

### 피드(Feed)
- [#018 피드(Feed) 도메인 개발 및 단위테스트](https://ddururiiiiiii.tistory.com/640)
- [#019 피드(Feed) 도메인 API 구현 및 테스트](https://ddururiiiiiii.tistory.com/641)

### 좋아요(Like)
- [#020 좋아요(Like) 도메인 개발 및 단위테스트](https://ddururiiiiiii.tistory.com/642)
- [#021 좋아요(Like) 도메인 API 구현 및 테스트](https://ddururiiiiiii.tistory.com/643)

### 댓글(Comment)
- [#022 댓글(Comment) 도메인 구현 및 단위테스트](https://ddururiiiiiii.tistory.com/644)
- [#023 댓글(Comment) API 구현 및 테스트](https://ddururiiiiiii.tistory.com/645)
- [#024 JWT 토큰이 없는데도 200 OK 가 뜬다? (해결방법)](https://ddururiiiiiii.tistory.com/646)

깃 테스트1
깃 테스트23
