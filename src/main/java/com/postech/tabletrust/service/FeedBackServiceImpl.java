package com.postech.tabletrust.service;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.entity.FeedBack;
import com.postech.tabletrust.entity.Reservation;
import com.postech.tabletrust.entity.Restaurant;
import com.postech.tabletrust.exception.InvalidReservationException;
import com.postech.tabletrust.repository.FeedBackRepository;
import com.postech.tabletrust.repository.ReservationRepository;
import com.postech.tabletrust.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements FeedBackService{

    private final FeedBackRepository feedBackRepository;
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Page<FeedBack> listFeedBackByRestaurantId(Pageable pageable, UUID restaurantId) {
        Restaurant restaurant = this.restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new IllegalArgumentException("Restaurant not found for ID " + restaurantId));
        return this.feedBackRepository.findByRestaurantId(restaurantId, pageable);
    }

    @Override
    public FeedBack create(FeedBackCreateDTO feedBackCreateDTO) throws Exception {
        LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
        //A regra criada é de que o customer so pode dar seu feedback 3 horas depois de iniciada a reserva
        // E se o status da reserva é approved = true senao quer dizer que ela foi anulada e o feedback nao pode ser feito

        Optional<Reservation> reservation = this.reservationRepository.findById(feedBackCreateDTO.reservationId());
        if (! reservation.isPresent()) {
            throw new InvalidReservationException("A reserva informada nao foi encontrada");
        }

        Reservation resa = reservation.get();

        if ( !resa.getApproved() || !resa.getReservationDate().isBefore(threeHoursAgo)){
            throw new InvalidReservationException("A reserva informada foi anulada ou ainda nao terminou");
        }

        FeedBack newFeedback = new FeedBack(feedBackCreateDTO);
        return this.feedBackRepository.save(newFeedback);
    }

    @Override
    public FeedBack findById(UUID id) {
        return this.feedBackRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("ID não encontrado"));
    }

    @Override
    public Boolean deleteById(UUID id) {
        this.feedBackRepository.deleteById(id);
        return true;
    }
}
