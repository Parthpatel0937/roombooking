package com.example.roombooking.repository;

import com.example.roombooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository  extends JpaRepository<Room, Long> {
}
