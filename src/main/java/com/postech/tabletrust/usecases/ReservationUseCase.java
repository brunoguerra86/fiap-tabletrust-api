package com.postech.tabletrust.usecases;

import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entity.Customer;
import com.postech.tabletrust.entity.Reservation;
import com.postech.tabletrust.entity.Restaurant;
import com.postech.tabletrust.exception.ReservationNotAvailable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationUseCase {

    public static Reservation validateInsertReservation(List<Reservation> reservationList, Restaurant restaurant, Customer customer, Integer quantity, String date) {
        if (!(restaurant.getAvailableCapacity() > reservationList.size())){
            throw new ReservationNotAvailable("O restaurante não tem mesas disponíveis");
        }
        //TODO validar quantidade de pessoas multiplos de 4
        // return new Reservation (quant, customer, date, restaurant);

        return new Reservation(restaurant, customer, quantity, date);
    }

    public static void validarUpdateReserva(String strId, Reservation reservationDTOOld, @Valid ReservationDTO reservationDTONew, Customer customerDTO) {

        if (!reservationDTONew.getId().equals(strId)) {
            throw new IllegalArgumentException("ID da reserva incorreto");
        }
        if (reservationDTOOld == null) {
            throw new IllegalArgumentException("Reserva não encontrada");
        }

    }

}
