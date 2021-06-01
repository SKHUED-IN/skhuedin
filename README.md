# 🌳 SKHU 사람 도서관 시스템 🌳<br/>SKHUED-IN (2021.02.27 ~ 2021.06.~ ing)
## `재학생들의 가장 큰 고민인 진로에 대하여, 같은 고민을 가지고 풀어나갔던 졸업생들이 꿀팁을 주면 좋겠다!` 는 마음으로 시작한 프로젝트 입니다. 
---
## 이용 방법
- 재학생과 졸업생은 `스스로를 책으로 등록` 하여  `1)자신의 학창시절 2)취업 준비 3)대학생으로 다시 돌아간다면?` 
   등 등 다양한 주제로 게시글을 쓰며 <br/> 책을 완성 시켜 나간다. 
- 궁금한 점이 있다면 `질문/댓글` 을 통하여 알아간다. 

---

## 기대효과 
- 졸업 후 단절되었던 선후배간 관계를 이어주고 유지시킬 수 있다.
- 선배님의 다양한 경험담과 해당 직종을 얻기 위해 했던 노력들을 게시물로 볼 수 있으므로, 간접적으로 알 수 있어 진로를 위해서 어떤 노력을 해야하는지 방향성을 잡을 수 있다. (삽질하는 시간을 줄이자)
- 대학생이 알고 있는 지식과 실무에서 사용하는 지식의 괴리감을 줄일 수 있다. 
- 졸업생들 간의 커뮤니티가 형성되어 졸업 후에도 학교에 대한 소속감을 가질 수 있고 각각의 직무에 대한 정보 공유도 이루어질 수 있다.

--- 

## 시작하기
build.gradle
```

plugins {
    id 'org.springframework.boot' version '2.4.4'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-validation'

    implementation('io.springfox:springfox-swagger2:2.9.2') {
        exclude module: 'swagger-annotations'
        exclude module: 'swagger-models'
    }
    implementation 'io.springfox:springfox-swagger-ui:2.9.2'
    implementation 'io.swagger:swagger-annotations:1.5.21'
    implementation 'io.swagger:swagger-models:1.5.21'

    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.10.0'

    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.h2database:h2'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'

    compile 'io.jsonwebtoken:jjwt-api:0.11.2'
    runtime 'io.jsonwebtoken:jjwt-impl:0.11.2',
            'io.jsonwebtoken:jjwt-jackson:0.11.2'
}

test {
    useJUnitPlatform()
}
```

## 배포 (무중단 배포)
* [AWS](https://aws.amazon.com/ko/)
    * S3
    * EC2
    * Travis CI
    * Nginx

## 사용 기술
* IntelliJ IDEA - IDE
* Spring Boot - 웹 프레임워크
* Java
* Gradle - 의존성 관리 프로그램
* Tomcat - 웹 애플리케이션 서버
* H2 - 개발 중 사용 내장 디비
* MySQL - 데이터베이스 (예정)
* JUnit - 테스트 코드 작성 (코드 검증)

---


