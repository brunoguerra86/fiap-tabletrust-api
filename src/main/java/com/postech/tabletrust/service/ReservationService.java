package com.postech.tabletrust.service;
import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.entities.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    Reservation createReservation(Reservation reservation, List<Reservation> reservationList, CustomerDTO customerDTO);
    Reservation findReservation(UUID reservationUUID);
    List<Reservation> findRestaurantReservation(UUID restaurantId); //todo customer e restaurant?
    List<Reservation> findCustomerReservation(UUID customerId); //todo customer e restaurant?
    Reservation updateReservation(Reservation newReservation);
    void deleteReservation(UUID id);
    List<Reservation> listAllReservations(); //todo ser a mesma acao q usa o id de restaurant
}
