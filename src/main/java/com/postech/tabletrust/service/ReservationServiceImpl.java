package com.postech.tabletrust.service;

import com.postech.tabletrust.Repository.ReservationRepository;
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
    public Reservation NewReservation(Reservation reservation){
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
    public Reservation findReservation(UUID id){
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("reserva não encontrada"));
    }
    @Override
    public Reservation updateReservation(UUID id, Reservation newReservation){
        Reservation reservation = findReservation(id);
        if (newReservation.getId() != null && !reservation.getId().equals(newReservation.getId())) {
            throw new IllegalArgumentException("reserva não apresenta o ID correto");
        }
        newReservation.setId(id);
        if (newReservation.getRestaurantId() == null ){
            throw new IllegalArgumentException("reserva não apresenta o RestaurantId correto");
        }
        if (newReservation.getCustomerId() == null ){
            throw new IllegalArgumentException("reserva não apresenta o CustomerId correto");
        }
        reservation = newReservation;
        return reservationRepository.save(reservation);
    }
    @Override
    public void deleteReservation(UUID id){
        Reservation reservation = findReservation(id);
        reservationRepository.delete(reservation);
    }
    @Override
    public List<Reservation> listReservations() {
        return reservationRepository.findAll();
    }
}
