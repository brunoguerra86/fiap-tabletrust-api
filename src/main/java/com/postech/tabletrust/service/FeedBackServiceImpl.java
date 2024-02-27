package com.postech.tabletrust.service;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import com.postech.tabletrust.entities.FeedBack;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.repository.FeedBackRepository;
import com.postech.tabletrust.repository.ReservationRepository;
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

    @Override
    public Page<FeedBack> listFeedback(Pageable pageable) {
        return null;
    }

    @Override
    public FeedBack create(FeedBackCreateDTO feedBackCreateDTO) throws Exception {
        LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
        //A regra criada é de que o customer so pode dar seu feedback 3 horas depois de iniciada a reserva
        // E se o status da reserva é approved = true senao quer dizer que ela foi anulada e o feedback nao pode ser feito

        Optional<Reservation> reservation = reservationRepository.findById(feedBackCreateDTO.reservationId());
        if (! reservation.isPresent()) {
            throw new EntityNotFoundException("A reserva informada nao foi encontrada");
        }

        Reservation resa = reservation.get();

        if ( !resa.getApproved() && !resa.getReservationDate().isBefore(threeHoursAgo)){
            throw new Exception("A reserva informada foi anulada ou ainda nao terminou");
        }

        FeedBack newFeedback = new FeedBack(feedBackCreateDTO);
        return this.feedBackRepository.save(newFeedback);
    }

    @Override
    public FeedBack findById(UUID id) {
        return this.feedBackRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("ID não encontrado"));
    }

    @Override
    public void deleteById(UUID id) {
        this.feedBackRepository.deleteById(id);
    }

}
