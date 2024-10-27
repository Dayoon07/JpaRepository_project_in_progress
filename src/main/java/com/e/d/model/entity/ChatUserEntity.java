package com.e.d.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "CHATUSER")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatUserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid; // 사용자 ID
	
	@Column(nullable = false)
	private String username; // 사용자 이름
	
	@Column(nullable = false)
	private String useremail; // 사용자 이메일
	
	@Column(nullable = false)
	private String userpassword; // 사용자 비밀번호
	
	@Column(nullable = false)
	private String userbio; // 사용자 소개 (CLOB 사용)
	
	@Column(nullable = false)
	private String userphone; // 사용자 전화번호
	
	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt; // 가입 일자
	
	@Column(name = "last_login")
	private Timestamp lastLogin; // 최근 로그인 일자
	
	@Column(nullable = true, columnDefinition = "VARCHAR2(10) DEFAULT 'offline'")
	private String status; // 유저 상태 (online, offline, away 등)
}
