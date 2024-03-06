package com.postech.tabletrust.usecases;

import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateReservationUseCase {

    public Reservation execute(Reservation reservation, ReservationRepository reservationRepository){
        try {

            //TODO
        /*Restaurant restaurant = findRestaurant(reservation.getRestaurantId());
        if (restaurant == null) {
            throw new IllegalArgumentException("reserva não apresenta o ID do Restaurante correto");
        }
        Customer customer = findCustomer(reservation.getCustomerId());
        if (customer == null) {
            throw new IllegalArgumentException("reserva não apresenta o ID do Cliente correto");
        }
         */
            reservation = reservationRepository.save(reservation);

        } catch (Exception e) {
            log.error("error creating a reservation to customer [{}], restaurant [{}]", reservation.getCustomerId(), reservation.getRestaurantId());
        }
        return reservation;
    }

}
