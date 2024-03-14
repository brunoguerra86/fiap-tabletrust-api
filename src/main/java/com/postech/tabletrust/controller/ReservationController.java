package com.postech.tabletrust.controller;

import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entities.Customer;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.entities.Restaurant;
import com.postech.tabletrust.gateways.CustomerGateway;
import com.postech.tabletrust.gateways.ReservationGateway;
import com.postech.tabletrust.gateways.RestaurantGateway;
import com.postech.tabletrust.usecases.ReservationUseCase;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationGateway reservationGateway;
    private final CustomerGateway customerGateway;
    private final RestaurantGateway restaurantGateway;

    public ReservationController(ReservationGateway reservationGateway, CustomerGateway customerGateway, RestaurantGateway restaurantGateway) {
        this.reservationGateway = reservationGateway;
        this.customerGateway = customerGateway;
        this.restaurantGateway = restaurantGateway;
    }

    @PostMapping("")
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        log.info("create reservation for customer [{}]", reservationDTO.getCustomerId());

        try {
            List<Reservation> reservationList = reservationGateway.findRestaurantReservationByDate(reservationDTO.getRestaurantId(), reservationDTO.getReservationDate());
            Customer customer = customerGateway.findCustomer(reservationDTO.getCustomerId());
            Restaurant restaurant = restaurantGateway.findRestaurantById(reservationDTO.getRestaurantId());
            Reservation reservation = ReservationUseCase.validateInsertReservation(reservationList, restaurant, customer,
                    reservationDTO.getQuantity(), reservationDTO.getReservationDate());

            Reservation reservationCreated = reservationGateway.createReservation(reservation);

            return new ResponseEntity<>(reservationCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/id={id}")
    public ResponseEntity<?> updateReservation(@PathVariable String id, @RequestBody @Valid ReservationDTO reservationNew) {
        log.info("PutMapping - updateReservation");
        try {
            Reservation reservationOld = reservationGateway.findReservation(id);
            Customer customer = customerGateway.findCustomer(reservationNew.getCustomerId());
            ReservationUseCase.validarUpdateReserva(id, reservationOld, reservationNew, customer);
            ReservationDTO newReservation = reservationGateway.updateReservation(reservationNew);
            return new ResponseEntity<>(newReservation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/id={id}")
    public ResponseEntity<?> deleteReservation(@PathVariable String id) {
        log.info("DeleteMapping - deleteReservation");
        try {
            reservationGateway.deleteReservation(id);
            return new ResponseEntity<>("reserva removida", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<ReservationDTO>> listAllReservations() {
        log.info("GetMapping - listReservations");
        List<ReservationDTO> reservations = reservationGateway.listAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> findReservation(@PathVariable String id) {
        log.info("GetMapping - FindReservation ");
        try {
            Reservation reservation = reservationGateway.findReservation(id);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/restaurantId={restaurantId}")
    public ResponseEntity<?> findRestaurantReservation(@PathVariable String restaurantId) {
        log.info("GetMapping - FindRestaurantReservation");
        try {
            List<ReservationDTO> reservations = reservationGateway.findRestaurantReservation(restaurantId);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/customerId={customerId}")
    public ResponseEntity<?> findCustomerReservation(@PathVariable String customerId) {
        log.info("GetMapping - findCustomerReservation");
        try {
            List<ReservationDTO> reservations = reservationGateway.findCustomerReservation(customerId);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/restaurantId/{restaurantId}")
    public ResponseEntity<?> findRestaurantReservationByDate(@PathVariable String restaurantId, @RequestParam String date) {
        log.info("find reservation from restaurant {} on date {}", restaurantId, date);
        try {
            List<ReservationDTO> reservations = reservationGateway.findRestaurantReservationByDate(restaurantId, date)
                    .stream()
                    .map(ReservationDTO::new).collect(Collectors.toList());
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}

