package com.postech.tabletrust.service;

import com.postech.tabletrust.repository.ReservationRepository;
import com.postech.tabletrust.entity.Reservation;
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

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
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
