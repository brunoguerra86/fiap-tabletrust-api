package com.postech.tabletrust.service;

import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.repository.ReservationRepository;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.usecases.CreateReservationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final CreateReservationUseCase createReservationUseCase;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
       return createReservationUseCase.execute(reservationDTO, reservationRepository);
    }

    @Override
    public ReservationDTO updateReservation(UUID id, ReservationDTO newReservation) {
        Reservation reservation = Reservation.getInstance(findReservation(id));
        if (newReservation.getId() != null && !reservation.getId().equals(newReservation.getId())) {
            throw new IllegalArgumentException("reserva não apresenta o ID correto");
        }
        newReservation.setId(id.toString());
        if (newReservation.getRestaurantId() == null) {
            throw new IllegalArgumentException("reserva não apresenta o RestaurantId correto");
        }
        if (newReservation.getCustomerId() == null) {
            throw new IllegalArgumentException("reserva não apresenta o CustomerId correto");
        }
        reservation = Reservation.getInstance(newReservation);
        return new ReservationDTO(reservationRepository.save(reservation));
    }

    @Override
    public void deleteReservation(UUID id) {
        Reservation reservation = Reservation.getInstance(findReservation(id));
        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationDTO findReservation(UUID id) {
        return new ReservationDTO(reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("reserva não encontrada")));
    }

    @Override
    public List<ReservationDTO> findCustomerReservation(UUID customerId) {
        return reservationRepository.findAllByCustomerId(customerId)
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }


    @Override
    public List<ReservationDTO> findRestaurantReservation(UUID restaurantId) {
        return reservationRepository.findAllByRestaurantId(restaurantId)
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> listAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }
}
