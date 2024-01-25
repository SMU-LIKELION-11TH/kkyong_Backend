# 꾱 (Kkyong)
> 공공 서비스들을 쉽게 예약하고 이용할 수 있도록 도와줄 수 있는 서비스

<p align="center">
  <img src = "https://github.com/SMU-LIKELION-11TH/kkyong_Backend/assets/75938496/bd7535c4-4fe4-4c00-88b3-bd1ff13fb82f">
</p>

## 프로젝트 소개
멋쟁이 사자처럼 11th 중앙 해커톤 출품작

### 팀원 소개

|     팀원     |                       역할                        |          책임                        |
| :---------: | :----------------------------------------------: | :---------------------------------: |
|  윤석현  |     PM , 프론트엔드 개발   | 화면 구현 및 통신 |
| 박서경  |    프론트엔드 개발  |  화면 구현 및 통신  |
|  박민수  | 백엔드 개발 | REST API 개발, 배포  |
|  김훈  | 백엔드 개발  | REST API 개발, 배포 |
| 송주혜 | 디자인, 기획  |  UI 설계 및 디자인  |

### 기능 
- 회원가입 시 기입한 지역 정보를 바탕으로 공공 서비스를 우선 안내하여 접근을 용이하게 함
- 예약 시 필요한 정보를 최소화, 프로세스를 단순화 하여 빠른 예약이 가능하게 함
- 즐겨찾기 기능을 통해 자주 이용하는 공공 서비스에 빠르게 접근할 수 있게 함

### 사용 기술
#### 백엔드
- Java 17
- Spring Boot 2.7.14
- MySQL 8.0
- Docker & Docker-compose
- Redis

#### 프론트엔드
- HTML5
- CSS3
- JavaScript

#### 디자인, 기획
- Figma

### 주요 기능
![image](https://github.com/SMU-LIKELION-11TH/kkyong_Backend/assets/75938496/dea32bce-dcc9-4e7b-9008-d41393217aa9)
![image](https://github.com/SMU-LIKELION-11TH/kkyong_Backend/assets/75938496/1a7b3b76-6e9f-4652-a3d4-3ea7ded180af)
![image](https://github.com/SMU-LIKELION-11TH/kkyong_Backend/assets/75938496/0d358d1f-09c1-487d-8ca7-f248a4de6211)

### 시스템 아키텍처
![Desktop - 1](https://github.com/SMU-LIKELION-11TH/kkyong_Backend/assets/75938496/6ce357e3-14a8-4bc1-87ac-e11fe7890d30)

### ERD 설계
![꾱](https://github.com/SMU-LIKELION-11TH/kkyong_Backend/assets/75938496/b5ae0e31-679a-4bb8-b326-d99944ab6a82)

### docker-compose.yml
```YAML
version: '3.9'

services:

  db:
    image: mysql:latest
    volumes:
      - ./db_data:/var/lib/mysql
    ports:
      - "3306:3306"
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ...
      MYSQL_DATABASE: ...
      MYSQL_USER: ...
      MYSQL_PASSWORD: ...
  
  redis:
    image: redis:latest
    volumes:
      - redis_data:/data
    ports:
      - "6379:6379"
    restart: always

  app:
    image: pmsu2007/kkyong
    ports:
      - "8080:8080"
    restart: always
    depends_on:
      - db
      - redis

volumes:
  db_data:
  redis_data:
```
