package com.example.roombooking.service;

import com.example.roombooking.entity.Reservation;
import com.example.roombooking.entity.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ReservationService {
    Reservation create(Reservation reservation, String username);
    Reservation update(Long id, Reservation reservation, String username);
    void delete(Long id, String username);
    Reservation getById(Long id, String username);
    Page<Reservation> getAll(String username, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

}
