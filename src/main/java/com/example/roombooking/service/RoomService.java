package com.example.roombooking.service;

import com.example.roombooking.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    Room create(Room room);
    Room update(Long id, Room room);
    void delete(Long id);
    Room getById(Long id);
    Page<Room> getAll(Pageable pageable);
}
