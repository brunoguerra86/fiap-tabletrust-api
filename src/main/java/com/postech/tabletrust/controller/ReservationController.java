package com.postech.tabletrust.controller;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.gateways.CustomerGateway;
import com.postech.tabletrust.gateways.ReservationGateway;
import com.postech.tabletrust.repository.ReservationRepository;
import com.postech.tabletrust.service.CustomerService;
import com.postech.tabletrust.service.ReservationService;
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

    private final ReservationService reservationService;
    private final CustomerService customerService;
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationService reservationService, CustomerService customerService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.customerService = customerService;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDTO reservation) {
        log.info("create reservation for customer [{}]", reservation.getCustomerId());
        ReservationGateway reservationGateway = new ReservationGateway(reservationService, reservationRepository);
        CustomerGateway customerGateway = new CustomerGateway(customerService);
        try {
            List<Reservation> reservationList = reservationGateway.findRestaurantReservationByDate(reservation.getRestaurantId(), reservation.getReservationDate());
            CustomerDTO customerDTO = customerGateway.findCustomer(reservation.getCustomerId() );
            ReservationUseCase.validateInsertReservation(reservation, reservationList, customerDTO);

            ReservationDTO reservationCreated = reservationGateway.createReservation(reservation, reservationList, customerDTO);
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
        ReservationGateway reservationGateway = new ReservationGateway(reservationService, reservationRepository);
        CustomerGateway customerGateway = new CustomerGateway(customerService);
        try {
            ReservationDTO reservationOld = reservationGateway.findReservation(id);
            CustomerDTO customerDTO = customerGateway.findCustomer(reservationNew.getCustomerId() );
            ReservationUseCase.validarUpdateReserva(id, reservationOld, reservationNew, customerDTO);
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
        ReservationGateway reservationGateway = new ReservationGateway(reservationService, reservationRepository);
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
        ReservationGateway reservationGateway = new ReservationGateway(reservationService, reservationRepository);
        List<ReservationDTO> reservations = reservationGateway.listAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> findReservation(@PathVariable String id) {
        log.info("GetMapping - FindReservation ");
        ReservationGateway reservationGateway = new ReservationGateway(reservationService, reservationRepository);
        try {
            ReservationDTO reservation = reservationGateway.findReservation(id);
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
        ReservationGateway reservationGateway = new ReservationGateway(reservationService, reservationRepository);
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
        ReservationGateway reservationGateway = new ReservationGateway(reservationService, reservationRepository);
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
        ReservationGateway reservationGateway = new ReservationGateway(reservationService, reservationRepository);
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

