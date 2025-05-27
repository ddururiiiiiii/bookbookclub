
# 📚 BookBookClub (Monolith Version)
- **BookBookClub**은 책에 대한 생각과 감상을 자유롭게 공유할 수 있는 SNS 플랫폼입니다.  
- 간단한 UI와 외부 도서 API 연동을 통해 독서 경험을 나누고, 커뮤니티를 형성할 수 있습니다.
- 이 프로젝트는 초기 모놀리식 아키텍처 기반으로 설계되었으며, 추후 MSA 아키텍처로 발전시켰습니다.



<br>

----

<br>

## 📆 개발 기간 
- 2025.04.16 ~ 2025.05.02

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

## 🚀 프로젝트 개요
- **기술 스택**:
    - Back-End: Java, Spring Boot, JPA, MySQL
    - Infra: Docker, Redis
    - 인증: JWT, OAuth2 (Google, Naver)
    - 기타: QueryDSL, TDD, Lombok
- **아키텍처**: 모놀리식 (단일 애플리케이션)




<br>

----

<br>

## ✨ 주요 기능

- 회원가입 / 로그인 (소셜 로그인 포함)
- 이메일 인증 (Redis + MySQL)
- 피드 CRUD (책 감상/생각 공유)
- 좋아요 기능
- 통합 검색 (책 제목, 작가 등)
- API 응답 표준화 / 글로벌 예외 처리

<br>

----

<br>


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

## 🔗 MSA 전환 프로젝트
📢 이 프로젝트는 초기 모놀리식 구조로 시작했으며, MSA 아키텍처로 리팩토링한 프로젝트는 아래에서 확인할 수 있습니다.

- (진행중) BookBookClub-MSA (메인 소개용): [🔗 링크](https://github.com/ddururiiiiiii/BookBookClub-MSA)
<br>

----

<br>

## 📕 개발일지
- ** 링크를 클릭하면 개발일지를 작성한 블로그로 이동 됩니다.
- ** 모놀리식 아키텍처일 때, 개발했던 내용만 작성되어 있고 그 이후의 개발일지는 [MSA readme](https://github.com/ddururiiiiiii/BookBookClub-MSA/blob/main/README.md)에서 확인하실 수 있습니다. 

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

- 깃 브랜치 테스트 중1
- 깃 브랜치 테스트 중2
- 깃 브랜치 테스트 중3