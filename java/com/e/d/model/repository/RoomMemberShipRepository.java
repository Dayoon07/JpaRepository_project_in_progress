package com.e.d.model.repository;

import com.e.d.model.entity.RoomMemberShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomMemberShipRepository extends JpaRepository<RoomMemberShipEntity, Integer> {
    // 필요한 메소드 정의
}
