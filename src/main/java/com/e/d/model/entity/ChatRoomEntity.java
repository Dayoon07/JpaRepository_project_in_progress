package com.e.d.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "CHATROOM")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roomid; // 방 ID
	
	@Column(nullable = false)
	private String roomname; // 방 이름
	
	@Column(name = "roomnameinname", nullable = false) // 필드명 수정: roomnameinname
	private String roomnameinname; // 방 이름 내에 표시될 이름
	
	@Column(name = "created_at")
	private Timestamp createdAt; // 방 생성 일시

	@Column(name = "ownerid")
	private Integer ownerid; // 방장 ID (chatuser의 userid 참조)
	
	@Column(name = "chattext")
	private String chattext;
	
	@Column(name = "textuser")
	private String textuser;
}
