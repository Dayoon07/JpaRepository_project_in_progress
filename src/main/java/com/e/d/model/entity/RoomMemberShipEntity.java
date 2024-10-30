package com.e.d.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROOM_MEMBERSHIP")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomMemberShipEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private int membershipId;

    @Column(name = "roomid")
    private int roomid;

    @Column(name = "userid")
    private int userid;

    @Column(name = "joined_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinedAt;

    @Column(name = "role", columnDefinition = "VARCHAR2(10 CHAR) DEFAULT 'member'")
    private String role;
    
}
