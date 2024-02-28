package com.postech.tabletrust.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.postech.tabletrust.dto.ReservationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "tb_reservation")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID restaurantId;
    private UUID customerId;
    @NotNull(message = "A data não pode ser nula.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationDate;
    @NotNull(message = "A quantidade de lugares não pode ser nula.")
    private Integer quantity;

    public static Reservation getInstance(ReservationDTO reservationDTO) {
      return new Reservation(UUID.randomUUID(),
              UUID.fromString(reservationDTO.getRestaurantId()),
              UUID.fromString(reservationDTO.getCustomerId()),
              reservationDTO.getReservationDate(),
              reservationDTO.getQuantity());
    }
}
