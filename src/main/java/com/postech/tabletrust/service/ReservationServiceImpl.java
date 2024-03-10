package com.postech.tabletrust.service;

import com.postech.tabletrust.dto.CustomerDTO;
import com.postech.tabletrust.repository.ReservationRepository;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.usecases.CreateReservationUseCase;
import com.postech.tabletrust.usecases.FindReservationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final CreateReservationUseCase createReservationUseCase;
    private final FindReservationUseCase findReservationUseCase;

    @Override
    public Reservation createReservation(Reservation reservation, List<Reservation> reservationList, CustomerDTO customerDTO) {
        return createReservationUseCase.validateInsertReservation(reservation, reservationList, customerDTO, reservationRepository);
    }

    @Override
    public Reservation updateReservation(Reservation newReservation) {
        return reservationRepository.save(newReservation);
    }

    @Override
    public void deleteReservation(UUID id) {
        Reservation reservation = findReservation(id);
        reservationRepository.delete(reservation);
    }

    @Override
    public Reservation findReservation(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("reserva não encontrada"));
    }

    @Override
    public List<Reservation> findRestaurantReservation(UUID restaurantId) {
        return reservationRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Reservation> findCustomerReservation(UUID customerId) {
        return reservationRepository.findAllByCustomerId(customerId);
    }


    @Override
    public List<Reservation> listAllReservations() {
        return reservationRepository.findAll();
    }
}
