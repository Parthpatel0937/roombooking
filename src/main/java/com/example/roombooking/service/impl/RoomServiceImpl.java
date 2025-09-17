package com.example.roombooking.service.impl;

import com.example.roombooking.entity.Room;
import com.example.roombooking.repository.RoomRepository;
import com.example.roombooking.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Override
    public Room create(Room room) {
        return roomRepository.save(room);
    }
    @Override
    public Room update(Long id, Room room) {
        Room existing = getById(id);
        existing.setName(room.getName());
        existing.setType(room.getType());
        existing.setDescription(room.getDescription());
        existing.setCapacity(room.getCapacity());
        existing.setActive(room.isActive());
        return roomRepository.save(existing);
    }
    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }
    @Override
    public Room getById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }
    @Override
    public Page<Room> getAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

}
