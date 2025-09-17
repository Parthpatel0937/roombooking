package com.example.roombooking.controller;

import com.example.roombooking.entity.Room;
import com.example.roombooking.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping
    public Page<Room> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return roomService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Room get(@PathVariable Long id) {
        return roomService.getById(id);
    }

    @PostMapping("postRooms")
    @PreAuthorize("hasRole('ADMIN')")
    public Room create(@RequestBody Room room) {
        return roomService.create(room);
    }

    @PutMapping("/updateRooms/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Room update(@PathVariable Long id, @RequestBody Room room) {
        return roomService.update(id, room);
    }

    @DeleteMapping("/deleteRooms/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        roomService.delete(id);
    }

}
