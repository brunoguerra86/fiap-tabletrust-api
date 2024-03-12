package com.postech.tabletrust.gateways;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.repository.ReservationRepository;
import com.postech.tabletrust.service.ReservationService;
import com.postech.tabletrust.interfaces.IReservationGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
@Slf4j
public class ReservationGateway implements IReservationGateway {
    private final ReservationRepository reservationRepository;

    public ReservationGateway( ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {

        // reservationEntity = new Reservation(reservation);
        reservation = reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO) {
        Reservation reservationEntity = new Reservation(reservationDTO);
        reservationEntity = reservationService.updateReservation(reservationEntity);
        return new ReservationDTO(reservationEntity);
    }

    @Override
    public void deleteReservation(String strId) {
        UUID uuid = UUID.fromString(strId);
        reservationService.deleteReservation(uuid);
    }

    @Override
    public List<ReservationDTO> findRestaurantReservation(String restaurantId) {
        UUID uuid = UUID.fromString(restaurantId);
        List<Reservation> reservationEntityList = reservationService.findRestaurantReservation(uuid);
        return new ReservationDTO().toList(reservationEntityList);
    }

    @Override
    public List<ReservationDTO> findCustomerReservation(String customerId) {
        UUID uuid = UUID.fromString(customerId);
        List<Reservation> reservationEntityList = reservationService.findCustomerReservation(uuid);
        return new ReservationDTO().toList(reservationEntityList);
    }

    @Override
    public ReservationDTO findReservation(String strId) {
        if (strId == null) {
            return new ReservationDTO();
        }
        UUID uuid = UUID.fromString(strId);
        return new ReservationDTO(reservationService.findReservation(uuid));
    }

    @Override
    public List<ReservationDTO> listAllReservations() {
        List<Reservation> reservationEntityList = reservationService.listAllReservations();

        return reservationEntityList
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findRestaurantReservationByDate(String restaurantId, String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            LocalDateTime start = dateTime.toLocalDate().atTime(LocalTime.MIN);
            LocalDateTime end = dateTime.toLocalDate().atTime(LocalTime.MAX);

            Optional<List<Reservation>> reservationList = reservationRepository.findAllByReservationDateBetween(start, end);
            //return reservationList.map(list -> list.stream().map(ReservationDTO::new).collect(Collectors.toList()))
            return reservationList
                    .orElse(Collections.emptyList());
        } catch (Exception e){
            log.error("Error processing reservation search by restaurant {} and date {}", restaurantId, date);
            throw e;
        }

    }
}
