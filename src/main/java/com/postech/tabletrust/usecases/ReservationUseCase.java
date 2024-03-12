package com.postech.tabletrust.usecases;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entities.Customer;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.entities.Restaurant;
import com.postech.tabletrust.exception.ReservationNotAvailable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationUseCase {



    public static void validateInsertReservation( List<Reservation> reservationList, Customer customer, String quantity, String date) {
        Restaurant restaurant = reservationList.stream().findFirst().get().getRestaurant();
        if (!(restaurant.getAvailableCapacity() > reservationList.size())){
            throw new ReservationNotAvailable("O restaurante não tem mesas disponíveis");
        }
        //TODO validar quantidade de pessoas multiplos de 4
        // return new Reservation (quant, customer, date, restaurant);

    }

    public static void validarUpdateReserva(String strId, Reservation reservationDTOOld, Reservation reservationDTONew, Customer customerDTO) {

        validarReserva(reservationDTONew, customerDTO);
        if (!reservationDTONew.getId().equals(strId)) {
            throw new IllegalArgumentException("ID da reserva incorreto");
        }
        if (reservationDTOOld == null) {
            throw new IllegalArgumentException("Reserva não encontrada");
        }

    }

}
