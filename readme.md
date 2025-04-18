
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

## 💻✨ 주요 기능
- 회원 가입 및 로그인 (일반 + 소셜 로그인 준비 중)
- 도서 검색 (외부 도서 API 연동 예정)
- 책 관련 게시글 작성, 조회, 수정, 삭제
- 게시글 목록 페이징 및 키워드 검색 기능
- API 응답 표준화, 예외 처리 공통화


<br>

----

<br>

## 🔧 사용 기술

| 구분           | 기술                                                          |
|----------------|---------------------------------------------------------------|
| **Backend**    | Java 17, Spring Boot, Spring MVC, JPA, QueryDSL              |
| **Database**   | MySQL                                                         |
| **Frontend**   | JavaScript (ES6), Thymeleaf                                   |
| **Security**   | Spring Security, BCryptPasswordEncoder                        |
| **Testing**    | JUnit, Mockito                                                |
| **Tooling**    | Git, Lombok, P6Spy, Logback                                   |


<br>

----

<br>

## 📁 프로젝트 구조
~~~
bookbookclub/
├── domain/             # 핵심 도메인 로직
├── application/        # 서비스 계층
├── api/                # 컨트롤러
├── common/             # 공통 응답, 예외 처리 등
└── resources/
    └── application.yml # 환경 설정

~~~


<br>

----

<br>

## 📕 개발일지
** 링크를 클릭하면 개발일지를 작성한 블로그로 이동 됩니다.

- [#000 프로젝트 생성 (프로젝트 생성, MySQL 연결, 개발 편의 설정, 패키지 설정 등)](https://ddururiiiiiii.tistory.com/598)
- [#001 회원 도메인(User) 개발](https://ddururiiiiiii.tistory.com/604)
- [#002 회원(User) 도메인 단위 테스트](https://ddururiiiiiii.tistory.com/605)

