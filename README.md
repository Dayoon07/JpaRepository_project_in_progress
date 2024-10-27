# 채팅 애플리케이션 데이터베이스 스키마

이 문서는 채팅 애플리케이션의 데이터베이스 스키마에 대한 개요를 제공하며, 사용된 테이블과 그 관계를 설명합니다.

## 테이블

### 1. `chatroom`

| 열 이름             | 데이터 유형             | 설명                                                 |
|---------------------|-----------------------|-----------------------------------------------------|
| `roomid`            | NUMBER                | 각 채팅방의 고유 식별자 (기본 키).                  |
| `roomname`          | VARCHAR2(255 CHAR)    | 채팅방의 이름.                                     |
| `roomnameinname`    | VARCHAR2(255 CHAR)    | 채팅방의 표시 이름.                                 |
| `created_at`        | TIMESTAMP             | 채팅방이 생성된 일시 (기본값: 현재 시각).           |
| `ownerid`           | NUMBER                | 방장 사용자 ID (외래 키로 `chatuser` 참조).        |
| `chattext`          | CLOB                  | 채팅 메시지의 내용.                                 |
| `textuser`          | VARCHAR2(255 CHAR)    | 마지막 메시지를 보낸 사용자 이름.                   |

### 2. `chatuser`

| 열 이름             | 데이터 유형             | 설명                                                 |
|---------------------|-----------------------|-----------------------------------------------------|
| `userid`            | NUMBER                | 각 사용자의 고유 식별자 (기본 키).                  |
| `username`          | VARCHAR2(255 CHAR)    | 사용자의 사용자 이름.                               |
| `useremail`         | VARCHAR2(255 CHAR)    | 사용자의 이메일 주소.                               |
| `userpassword`      | VARCHAR2(255 CHAR)    | 사용자 인증을 위한 비밀번호.                        |
| `userbio`           | CLOB                  | 사용자의 소개글.                                   |
| `userphone`         | VARCHAR2(13)          | 사용자의 전화번호.                                   |
| `created_at`        | TIMESTAMP             | 사용자가 생성된 일시 (기본값: 현재 시각).           |
| `last_login`        | TIMESTAMP             | 사용자의 마지막 로그인 시각.                         |
| `status`            | VARCHAR(10)           | 사용자의 현재 상태 (기본값: 'offline').             |

### 3. `room_membership`

| 열 이름             | 데이터 유형             | 설명                                                 |
|---------------------|-----------------------|-----------------------------------------------------|
| `membership_id`     | NUMBER                | 각 방 참여의 고유 식별자 (기본 키).                 |
| `roomid`            | NUMBER                | 채팅방 ID (외래 키로 `chatroom` 참조).              |
| `userid`            | NUMBER                | 사용자 ID (외래 키로 `chatuser` 참조).              |
| `joined_at`         | TIMESTAMP             | 방에 참여한 일자 (기본값: 현재 시각).                |
| `role`              | VARCHAR2(10)          | 사용자의 역할 (예: 관리자, 멤버 등, 기본값: 'member').|
