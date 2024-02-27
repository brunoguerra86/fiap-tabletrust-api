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

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> NewReservation(@Valid @RequestBody Reservation reservation) {
        //log.info("PostMapping - createReservation");
        try {
            Reservation reservationCreated = reservationService.NewReservation(reservation);
            return new ResponseEntity<>(reservationCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateReservation(
            @PathVariable String id,
            @RequestBody @Valid Reservation reservation) {
        log.info("PutMapping - updateReservation");
        try {
            UUID uuid = UUID.fromString(id);
            Reservation newReservation = reservationService.UpdateReservation(uuid, reservation);
            return new ResponseEntity<>(newReservation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteReservation(@PathVariable String id) {
        log.info("DeleteMapping - deleteReservation");
        try {
            var uuid = UUID.fromString(id);
            reservationService.DeleteReservation(uuid);
            return new ResponseEntity<>("reserva removida", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> ListReservations() {
        log.info("GetMapping - listReservations");
        List<Reservation> reservations = reservationService.ListReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> FindReservation(@PathVariable String id) {
        log.info("GetMapping - FindReservation ");
        try {
            UUID uuid = UUID.fromString(id);
            Reservation reservation = reservationService.FindReservation(uuid);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/restaurantId={restaurantId}")
    public ResponseEntity<?> FindRestaurantReservation(@PathVariable String restaurantId) {
        log.info("GetMapping - FindRestaurantReservation");
        try {
            UUID uuid = UUID.fromString(restaurantId);
            List<Reservation> reservations = reservationService.FindRestaurantReservation(uuid);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/customerId={customerId}")
    public ResponseEntity<?> FindCustomerReservation(@PathVariable String customerId) {
        log.info("GetMapping - FindCustomerReservation");
        try {
            UUID uuid = UUID.fromString(customerId);
            List<Reservation> reservations = reservationService.FindCustomerReservation(uuid);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

