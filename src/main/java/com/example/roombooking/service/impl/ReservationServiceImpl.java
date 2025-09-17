package com.example.roombooking.service.impl;

import com.example.roombooking.entity.*;
import com.example.roombooking.repository.ReservationRepository;
import com.example.roombooking.repository.RoomRepository;
import com.example.roombooking.repository.UserRepository;
import com.example.roombooking.service.ReservationService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Reservation create(Reservation reservation, String username) {
        AppUser user = userRepository.findByUsername(username).orElseThrow();
        reservation.setUser(user);
        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow();
        reservation.setRoom(room);
        if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
            var overlaps = reservationRepository.findConfirmedOverlapping(room, reservation.getStartTime(), reservation.getEndTime());
            if (!overlaps.isEmpty()) throw new IllegalArgumentException("Time slot overlaps with existing confirmed reservation");
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Long id, Reservation reservation, String username) {
        Reservation existing = reservationRepository.findById(id).orElseThrow();
        if (!isAdmin(username) && !existing.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        existing.setPrice(reservation.getPrice());
        existing.setStartTime(reservation.getStartTime());
        existing.setEndTime(reservation.getEndTime());
        existing.setStatus(reservation.getStatus());
        if (existing.getStatus() == ReservationStatus.CONFIRMED) {
            var overlaps = reservationRepository.findConfirmedOverlapping(existing.getRoom(), existing.getStartTime(), existing.getEndTime());
            overlaps.removeIf(x -> x.getId().equals(existing.getId()));
            if (!overlaps.isEmpty()) throw new IllegalArgumentException("Time slot overlaps with existing confirmed reservation");
        }
        return reservationRepository.save(existing);
    }

    @Override
    public void delete(Long id, String username) {

        Reservation existing = reservationRepository.findById(id).orElseThrow();
        if (!isAdmin(username) && !existing.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }

        if (existing.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation already cancelled");
        }

        existing.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(existing);
    }

    @Override
    public Reservation getById(Long id, String username) {
        Reservation existing = reservationRepository.findById(id).orElseThrow();
        if (!isAdmin(username) && !existing.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        return existing;
    }

    @Override
    public Page<Reservation> getAll(String username, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        AppUser user = userRepository.findByUsername(username).orElseThrow();
        Specification<Reservation> spec = buildSpec(status, minPrice, maxPrice);

        if (!isAdmin(username)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("user").get("id"), user.getId()));
        }
        return reservationRepository.findAll(spec, pageable);
    }

    private boolean isAdmin(String username) {
        return userRepository.findByUsername(username).map(u -> u.getRoles().contains(Role.ROLE_ADMIN)).orElse(false);
    }

    private Specification<Reservation> buildSpec(ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null) predicates.add(cb.equal(root.get("status"), status));
            if (minPrice != null) predicates.add(cb.ge(root.get("price"), minPrice));
            if (maxPrice != null) predicates.add(cb.le(root.get("price"), maxPrice));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
