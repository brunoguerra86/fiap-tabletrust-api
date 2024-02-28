package com.postech.tabletrust.controller;

import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.service.ReservationService;
import jakarta.validation.Valid;
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
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDTO reservation) {
        log.info("create reservation for customer [{}]", reservation.getCustomerId());
        try {
            ReservationDTO reservationCreated = reservationService.createReservation(reservation);
            return new ResponseEntity<>(reservationCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido"); //feedback c mensagem especifica para erro generico
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // ?
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateReservation(
            @PathVariable String id,
            @RequestBody @Valid ReservationDTO reservation) {
        log.info("PutMapping - updateReservation");
        try {
            UUID uuid = UUID.fromString(id);
            ReservationDTO newReservation = reservationService.updateReservation(uuid, reservation);
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
            reservationService.deleteReservation(uuid);
            return new ResponseEntity<>("reserva removida", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<ReservationDTO>> ListReservations() {
        log.info("GetMapping - listReservations");
        List<ReservationDTO> reservations = reservationService.listAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> FindReservation(@PathVariable String id) {
        log.info("GetMapping - FindReservation ");
        try {
            UUID uuid = UUID.fromString(id);
            ReservationDTO reservation = reservationService.findReservation(uuid);
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
            List<ReservationDTO> reservations = reservationService.findRestaurantReservation(uuid);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/customerId={customerId}")
    public ResponseEntity<?> FindCustomerReservation(@PathVariable String customerId) {
        log.info("GetMapping - findCustomerReservation");
        try {
            UUID uuid = UUID.fromString(customerId);
            List<ReservationDTO> reservations = reservationService.findCustomerReservation(uuid);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

