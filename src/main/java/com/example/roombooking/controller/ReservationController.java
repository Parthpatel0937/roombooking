package com.example.roombooking.controller;

import com.example.roombooking.entity.Reservation;
import com.example.roombooking.entity.ReservationStatus;
import com.example.roombooking.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/getBookings")
    public Page<Reservation> list(@RequestParam(required=false) ReservationStatus status,
                                  @RequestParam(required=false) BigDecimal minPrice,
                                  @RequestParam(required=false) BigDecimal maxPrice,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        return reservationService.getAll(userDetails.getUsername(), status, minPrice, maxPrice, PageRequest.of(page, size));
    }

    @GetMapping("/getBookings/{id}")
    public ResponseEntity<Reservation> get(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(reservationService.getById(id, userDetails.getUsername()));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping("/bookrooms")
    public ResponseEntity<?> create(@RequestBody Reservation reservation, @AuthenticationPrincipal UserDetails userDetails) {

        Reservation saved = reservationService.create(reservation, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/updateBookings/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Reservation reservation, @AuthenticationPrincipal UserDetails userDetails) {

        Reservation updated = reservationService.update(id, reservation, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/cancelBookings/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            reservationService.delete(id, userDetails.getUsername());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(403).build();
        }
    }


}

