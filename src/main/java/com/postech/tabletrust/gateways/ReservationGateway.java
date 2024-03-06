package com.postech.tabletrust.gateways;

import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.service.ReservationService;
import com.postech.tabletrust.interfaces.IReservationGateway;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationGateway implements IReservationGateway {
    private final ReservationService reservationService;

    public ReservationGateway(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {

        Reservation reservationEntity = new Reservation(reservationDTO);
        reservationEntity = reservationService.createReservation(reservationEntity);
        return new ReservationDTO(reservationEntity);
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
}
