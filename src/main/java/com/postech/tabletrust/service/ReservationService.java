package com.postech.tabletrust.service;
import com.postech.tabletrust.dto.ReservationDTO;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservation);

    ReservationDTO findReservation(UUID reservationUUID);
    List<ReservationDTO> findCustomerReservation(UUID customerId); //todo customer e restaurant?
    List<ReservationDTO> findRestaurantReservation(UUID restaurantId);
    ReservationDTO updateReservation(UUID id, ReservationDTO newReservation);
    void deleteReservation(UUID id);
    List<ReservationDTO> listAllReservations(); //todo ser a mesma acao q usa o id de restaurant
}
