package com.postech.tabletrust.controller;

import jakarta.validation.Valid;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    @PostMapping
    public ResponseEntity<?> NewReservation(@RequestParam("customerId") String customerId,
                                             @RequestParam("restaurantId") String restaurantId,
                                             @Valid @RequestBody Reservation reservation) {
        log.info("PostMapping - createReservation");
        try {
            reservation.setCustomerId(UUID.fromString(customerId));
            reservation.setRestaurantId(UUID.fromString(restaurantId));
            Reservation reservationCreated = reservationService.NewReservation(reservation);
            return new ResponseEntity<>(reservationCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> listReservations() {
        log.info("GetMapping - listReservations");
        List<Reservation> reservations = reservationService.listReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findReservation(@PathVariable String id) {
        log.info("GetMapping - findReservation");
        try {
            UUID uuid = UUID.fromString(id);
            Reservation reservation = reservationService.findReservation(uuid);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping( "/{id}")
    public ResponseEntity<?> updateReservation(
            @PathVariable String id,
            @RequestBody @Valid Reservation reservation) {
        log.info("PutMapping - updateReservation");
        try {
            UUID uuid = UUID.fromString(id);
            Reservation newReservation = reservationService.updateReservation(uuid, reservation);
            return new ResponseEntity<>(newReservation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable String id) {
        log.info("DeleteMapping - deleteReservation");
        try {
            var uuid = UUID.fromString(id);
            reservationService.deleteReservation(uuid);
            return new ResponseEntity<>("reserva removida", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

