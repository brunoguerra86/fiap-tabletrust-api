package com.postech.tabletrust.service;
import com.postech.tabletrust.entities.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    Reservation NewReservation(Reservation reservation);

    Reservation FindReservation(UUID id);
    List<Reservation> FindCustomerReservation(UUID customerId);
    List<Reservation> FindRestaurantReservation(UUID restaurantId);

    Reservation UpdateReservation(UUID id, Reservation newReservation);

    void DeleteReservation(UUID id);

    List<Reservation> ListReservations();
}
