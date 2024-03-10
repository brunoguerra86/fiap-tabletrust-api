package com.postech.tabletrust.usecases;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.dto.ReservationDTO;
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


    private static void validarReserva(ReservationDTO reservationDTO, CustomerDTO customerDTO) {

        if (customerDTO == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        /* TODO
        if (restaurantDTO == null) {
            throw new IllegalArgumentException("Restaurante não encontrado");
        } */
        if (reservationDTO == null) {
            throw new IllegalArgumentException("Reserva não pode ser nula");
        }
    }

    public static void validateInsertReservation(ReservationDTO reservationDTO, List<Reservation> reservationList, CustomerDTO customerDTO) {
        Restaurant restaurant = reservationList.stream().findFirst().get().getRestaurant();
        if (restaurant.getAvailableCapacity() > reservationList.size()){
            validarReserva(reservationDTO, customerDTO);

        } else {
            throw new ReservationNotAvailable("O restaurante não tem mesas disponíveis");
        }

    }

    public static void validarUpdateReserva(String strId, ReservationDTO reservationDTOOld, ReservationDTO reservationDTONew, CustomerDTO customerDTO) {

        validarReserva(reservationDTONew, customerDTO);
        if (!reservationDTONew.getId().equals(strId)) {
            throw new IllegalArgumentException("ID da reserva incorreto");
        }
        if (reservationDTOOld == null) {
            throw new IllegalArgumentException("Reserva não encontrada");
        }

    }

}
