package com.postech.tabletrust.interfaces;

import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entities.Reservation;

import java.util.List;

public interface IReservationGateway {
    public ReservationDTO createReservation(ReservationDTO reservation);

    public ReservationDTO updateReservation(ReservationDTO reservation);

    public void deleteReservation(String strId);

    public List<ReservationDTO> findRestaurantReservation(String restaurantId);

    public List<ReservationDTO> findCustomerReservation(String customerId);

    public ReservationDTO findReservation(String strId);

    public List<ReservationDTO> listAllReservations();
}
