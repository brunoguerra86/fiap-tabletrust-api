package com.postech.tabletrust.usecases;

import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateReservationUseCase {
    private ReservationRepository reservationRepository;

    public ReservationDTO execute(ReservationDTO reservationDTO, ReservationRepository reservationRepository){
        try {
            Reservation newReservation = Reservation.getInstance(reservationDTO);

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
            newReservation = reservationRepository.save(newReservation);
            reservationDTO = new ReservationDTO(newReservation);

        } catch (Exception e) {
            log.error("error creating a reservation to customer [{}], restaurant [{}]", reservationDTO.getCustomerId(), reservationDTO.getRestaurantId());
        }
        return reservationDTO;
    }

}
