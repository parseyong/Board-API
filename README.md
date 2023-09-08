# Board-API
스프링부트/jpa를 이용하여 간단한 게시판 api서버를 만드는 레파지토리입니다.

# 목적
개인프로젝트를 진행하기 전에 간단한 CRUD명령을 querydsl,jpa를 이용하여 수행해보고 ,restful한 api설계,ERD 설계등을 해보면서 전체적인 백엔드 흐름을 익히기 위해 시작하였습니다. JWT와 security를 사용하여 회원 인증/인가를 구현하였으며 페이징처리를 querydsl을 통해 구현하였습니다.

# Postman api 명세서
https://documenter.getpostman.com/view/28802773/2s9Y5VSiRN

# 수정할 것
1. @Builder을 class선언이 아닌 constructor선언으로 변경
2. JwtFilter에서 GenericFilter를 BeanOncePerRequestFilter로 변경


# 사용 기술스택
<img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
