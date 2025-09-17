package com.example.roombooking.repository;

import com.example.roombooking.entity.Reservation;
import com.example.roombooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
    @Query("select r from Reservation r where r.room = :room and r.status = 'CONFIRMED' and r.startTime < :end and r.endTime > :start")
    List<Reservation> findConfirmedOverlapping(@Param("room") Room room, @Param("start") Instant start, @Param("end") Instant end);

}
