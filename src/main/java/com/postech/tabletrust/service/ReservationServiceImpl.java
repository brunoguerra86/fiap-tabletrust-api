package com.postech.tabletrust.service;

import com.postech.tabletrust.repository.ReservationRepository;
import com.postech.tabletrust.entities.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation NewReservation(Reservation reservation) {
        reservation.setId(UUID.randomUUID());

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
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation UpdateReservation(UUID id, Reservation newReservation) {
        Reservation reservation = FindReservation(id);
        if (newReservation.getId() != null && !reservation.getId().equals(newReservation.getId())) {
            throw new IllegalArgumentException("reserva não apresenta o ID correto");
        }
        newReservation.setId(id);
        if (newReservation.getRestaurantId() == null) {
            throw new IllegalArgumentException("reserva não apresenta o RestaurantId correto");
        }
        if (newReservation.getCustomerId() == null) {
            throw new IllegalArgumentException("reserva não apresenta o CustomerId correto");
        }
        reservation = newReservation;
        return reservationRepository.save(reservation);
    }

    @Override
    public void DeleteReservation(UUID id) {
        Reservation reservation = FindReservation(id);
        reservationRepository.delete(reservation);
    }

    @Override
    public Reservation FindReservation(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("reserva não encontrada"));
    }

    @Override
    public List<Reservation> FindCustomerReservation(UUID customerId) {
        return reservationRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Reservation> FindRestaurantReservation(UUID restaurantId) {
        return reservationRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Reservation> ListReservations() {
        return reservationRepository.findAll();
    }
}
