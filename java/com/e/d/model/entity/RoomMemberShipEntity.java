package com.e.d.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "ROOM_MEMBERSHIP")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomMemberShipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int membershipId; // 멤버십 ID

    @Column(nullable = false)
    private int roomid; // 채팅방 ID

    @Column(nullable = false)
    private int userid; // 사용자 ID

    @Column(name = "joined_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp joinedAt; // 방 참여 일자

    @Column(nullable = false, columnDefinition = "VARCHAR2(10) DEFAULT 'member'")
    private String role; // 역할 (예: 관리자, 멤버 등)
}
