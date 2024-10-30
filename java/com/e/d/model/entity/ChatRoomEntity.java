package com.e.d.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CHATROOM")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomid")
    private int roomid;

    @Column(name = "roomname", nullable = false)
    private String roomname;

    @Column(name = "roomnameinname", nullable = false)
    private String roomnameinname;

    @Column(name = "date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTime;

    @Column(name = "ownerid")
    private Integer ownerid;

    @Lob
    @Column(name = "chattext")
    private String chattext;

    @Column(name = "textuser")
    private String textuser;

    @Column(name = "sender")
    private String sender;
	
}
