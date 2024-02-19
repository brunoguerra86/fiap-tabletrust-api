package com.postech.tabletrust.service;
import com.postech.tabletrust.entities.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    Reservation NewReservation(Reservation reservation);

    Reservation findReservation(UUID id);

    Reservation updateReservation(UUID id, Reservation newReservation);

    void deleteReservation(UUID id);

    List<Reservation> listReservations();
}
