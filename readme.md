
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
- JPA 성능 최적화 (N+1 문제 해결, 지연/즉시 로딩, fetch join, QueryDSL 복잡 쿼리 최적화)
- 계층 분리 및 도메인 기반 구조 → 유지보수성과 확장성을 고려한 설계
- API 응답 표준화, 전역 예외 처리, 테스트 가능한 구조에 대한 고민과 적용
- 사이드 프로젝트로 실무에서 경험하지 못한 기술 적극 도입 (예: OAuth2, Redis, Kafka, Docker, CI/CD)
- 문제 해결 중심의 백엔드 개발자로 성장 → 사용자 중심의 서비스를 만드는 개발자 목표


<br>

----

<br>

## 💻✨ 주요 기능 구현 현황
| 구분                   | 구현 기능                                                                       |
| -------------------- | --------------------------------------------------------------------------- |
| **회원 가입 및 인증**       | 이메일 인증 기반 회원가입<br>Redis와 DB 동시 저장<br>이메일 인증 상태 확인 및 가입 제한<br>6개월 이내 재가입 불가능 |
| **로그인 / 인증**         | JWT 기반 로그인<br>Access/Refresh Token 관리 (Redis 활용)<br>AccessToken 블랙리스트 처리    |
| **소셜 로그인**           | Google, Naver 연동<br>Provider별 서비스 분리<br>닉네임 중복 시 예외 처리                      |
| **회원 정보 관리**         | 회원 조회 API (`/me`)<br>닉네임 중복 확인 API<br>프로필 이미지 업로드 및 저장 (로컬, 향후 S3 예정)       |
| **예외 처리 및 공통 응답 포맷** | `@RestControllerAdvice` 기반 글로벌 예외 처리<br>`ApiResponse<T>` 통일된 응답 포맷 적용       |
| **보안**               | URL 접근 제어 설정<br>비밀번호 암호화(BCrypt)                                            |
| **책(Book)**          | Book 도메인 CRUD<br>Kakao Book API 연동<br>중복 등록 시 예외 처리                         |
| **피드(Feed)**         | Feed 도메인 CRUD<br>좋아요 순 정렬 (주간/월간/연간/누적)<br>피드 검색<br>특정 회원의 피드·좋아요 목록 조회     |
| **좋아요(Like)**        | Like 도메인 CRUD<br>좋아요 토글 기능<br>랭킹 조회 시 피드 상세 포함                              |
| **댓글(Comment)**      | Comment 도메인 CRUD                                                            |
| **팔로우(Follow)**      | Follow 도메인 CRUD                                                             |
| **신고(Report)**       | 신고 도메인 구현<br>누적 시 피드 블라인드 처리                                                |
| **기타**               | 이메일 인증 내역 테이블 관리<br>로그인 이력/재가입 제어 로직<br>Spring Scheduler 기반 탈퇴 처리           |


<br>

----

<br>

## 🔧 사용 기술
## Backend
- Java 17
- Spring Boot 3
- Spring Security (JWT, OAuth2)
- JPA (Hibernate), QueryDSL (복잡 쿼리 최적화)
- Redis (Token 관리, 향후 캐싱 예정)
- MySQL

### Frontend
- Thymeleaf (기초 UI 구성)
- JavaScript (ES6)

### DevOps
- Gradle
- Git (Git Flow 적용 중, PR 전략 실험 중)
- Docker / Kubernetes (추후 도입 예정)
- Kafka (추후 도입 예정)

<br>

----

<br>

## 📁 프로젝트 구조
~~~
src/main/java/ddururi/bookbookclub/
├── domain/
│   ├── user/
│   ├── book/
│   ├── feed/
│   ├── like/
│   ├── comment/
│   ├── follow/
│   └── report/
└── global/
    ├── common/
    ├── config/
    ├── exception/
    ├── jwt/
    └── security/

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
- [#029 책(Book) - Spring WebClient로 외부 API 연동: KakaoBookClient 구현](https://ddururiiiiiii.tistory.com/653)
- [#033 책(Book) 중복 등록 시 예외 처리 하기](https://ddururiiiiiii.tistory.com/658)

### 피드(Feed)
- [#018 피드(Feed) 도메인 개발 및 단위테스트](https://ddururiiiiiii.tistory.com/640)
- [#019 피드(Feed) 도메인 API 구현 및 테스트](https://ddururiiiiiii.tistory.com/641)
- [#026 신고(Report) 도메인 구현 및 단위테스트](https://ddururiiiiiii.tistory.com/650)
- [#027 신고(Report) 누적 시, 블라인드 처리](https://ddururiiiiiii.tistory.com/651)
- [#028 좋아요 순 피드 조회 (주간/월간/연간/누적)](https://ddururiiiiiii.tistory.com/652)
- [#030 좋아요(Like) 랭킹에서 피드 상세정보 함께 내려주기](https://ddururiiiiiii.tistory.com/654)
- [#031 피드 검색 조회](https://ddururiiiiiii.tistory.com/656)
- [#032 각종 피드 조회 (특정 회원의 피드 목록 조회, 특정 회원이 좋아요 누른 피드 목록 조회)](https://ddururiiiiiii.tistory.com/657)

### 좋아요(Like)
- [#020 좋아요(Like) 도메인 개발 및 단위테스트](https://ddururiiiiiii.tistory.com/642)
- [#021 좋아요(Like) 도메인 API 구현 및 테스트](https://ddururiiiiiii.tistory.com/643)
- [#025 좋아요(Like) 토글 기능으로 변경하기](https://ddururiiiiiii.tistory.com/648)

### 댓글(Comment)
- [#022 댓글(Comment) 도메인 구현 및 단위테스트](https://ddururiiiiiii.tistory.com/644)
- [#023 댓글(Comment) API 구현 및 테스트](https://ddururiiiiiii.tistory.com/645)
- [#024 JWT 토큰이 없는데도 200 OK 가 뜬다? (해결방법)](https://ddururiiiiiii.tistory.com/646)

### 팔로우(Follow)
- [#034 팔로우(Follow) 도메인 구현 및 테스트](https://ddururiiiiiii.tistory.com/659)