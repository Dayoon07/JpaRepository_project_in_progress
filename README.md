# 채팅 웹 애플리케이션 설명

채팅 애플리케이션의 데이터베이스에 대한 설명
사용된 데이터베이스는 Oracle XE 21

## 테이블 정리

### 1. chatroom

| 필드명            | 타입                       | 비고                                       | 기본값                       | 설명                          |
|-------------------|---------------------------|-------------------------------------------|-----------------------------|-------------------------------|
| roomid            | NUMBER                    | PRIMARY KEY, GENERATED ALWAYS AS IDENTITY | -                           | 방의 고유 ID                 |
| roomname          | VARCHAR2(255 CHAR)       | NOT NULL                                  | -                           | 방의 이름                   |
| roomnameinname    | VARCHAR2(255 CHAR)       | NOT NULL                                  | -                           | 방의 이름 (내부 사용)       |
| date_time         | TIMESTAMP                 | -                                         | CURRENT_TIMESTAMP           | 방 생성 일시                 |
| ownerid           | NUMBER                    | REFERENCES chatuser(userid)              | -                           | 방장 ID                     |
| chattext          | CLOB                      | -                                         | -                           | 방의 채팅 텍스트             |
| textuser          | VARCHAR2(255 CHAR)       | -                                         | -                           | 메시지를 보낸 사용자 이름   |
| sender            | VARCHAR2(255 CHAR)       | -                                         | -                           | 메시지를 보낸 사람          |

### 2. chatuser

| 필드명            | 타입                       | 비고                                       | 기본값                       | 설명                          |
|-------------------|---------------------------|-------------------------------------------|-----------------------------|-------------------------------|
| userid            | NUMBER                    | PRIMARY KEY, GENERATED ALWAYS AS IDENTITY | -                           | 사용자 고유 ID                |
| username          | VARCHAR2(255 CHAR)       | NOT NULL                                  | -                           | 사용자 이름                 |
| useremail         | VARCHAR2(255 CHAR)       | NOT NULL                                  | -                           | 사용자 이메일               |
| userpassword      | VARCHAR2(255 CHAR)       | NOT NULL                                  | -                           | 사용자 비밀번호             |
| userbio           | CLOB                      | NOT NULL                                  | -                           | 사용자 소개                 |
| userphone         | VARCHAR2(13)             | NOT NULL                                  | -                           | 사용자 전화번호             |
| date_time         | TIMESTAMP                 | NOT NULL                                  | CURRENT_TIMESTAMP           | 사용자 생성 일시            |
| status            | VARCHAR2(255 CHAR)       | -                                         | 'Off'                       | 사용자 상태                 |

### 3. room_membership

| 필드명            | 타입                       | 비고                                       | 기본값                       | 설명                          |
|-------------------|---------------------------|-------------------------------------------|-----------------------------|-------------------------------|
| membership_id     | NUMBER                    | PRIMARY KEY, GENERATED ALWAYS AS IDENTITY | -                           | 멤버십의 고유 ID            |
| roomid            | NUMBER                    | REFERENCES chatroom(roomid)              | -                           | 방의 ID                     |
| userid            | NUMBER                    | REFERENCES chatuser(userid)              | -                           | 사용자의 ID                 |
| joined_at         | TIMESTAMP                 | -                                         | CURRENT_TIMESTAMP           | 방 참여 일시                 |
| role              | VARCHAR2(10)             | -                                         | 'member'                   | 방 내 역할 (예: 관리자, 멤버 등) |

### 4. chat_message

| 필드명            | 타입                       | 비고                                       | 기본값                       | 설명                          |
|-------------------|---------------------------|-------------------------------------------|-----------------------------|-------------------------------|
| message_id        | NUMBER                    | PRIMARY KEY, GENERATED ALWAYS AS IDENTITY | -                           | 메시지의 고유 ID            |
| roomid            | NUMBER                    | REFERENCES chatroom(roomid)              | -                           | 메시지가 속한 방의 ID      |
| userid            | NUMBER                    | REFERENCES chatuser(userid)              | -                           | 메시지를 보낸 사용자 ID     |
| chattext          | CLOB                      | NOT NULL                                  | -                           | 메시지 내용                 |
| date_time         | TIMESTAMP                 | -                                         | CURRENT_TIMESTAMP           | 메시지 전송 일시            |
| username          | VARCHAR2(255 CHAR)       | NOT NULL                                  | -                           | 메시지를 보낸 사용자 이름   |

## Notes

- 모든 테이블의 `PRIMARY KEY`는 `GENERATED ALWAYS AS IDENTITY`로 설정되어 자동으로 증가
- 기본값이 설정된 열은 해당 값이 명시되지 않을 경우 자동으로 적용
- `CLOB` 타입은 대용량 텍스트 데이터를 저장하는 데 사용
- Entity에서 Table name을 전부 대문자로 한 이유는 그렇게 안 하면 매핑이 잘 안되서 하기 싫은면 그냥 쿼트부호 사용
