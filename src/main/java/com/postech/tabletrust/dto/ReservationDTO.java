package com.postech.tabletrust.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.postech.tabletrust.entities.Reservation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class ReservationDTO {
    private String id;
    private String restaurantId;
    private String customerId;
    @NotNull(message = "A data não pode ser nula.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String reservationDate;
    @NotNull(message = "A quantidade de lugares não pode ser nula.")
    private Integer quantity;

    public ReservationDTO() {
    }
    public ReservationDTO(Reservation reservation) {
        if (reservation != null) {
            this.id = reservation.getId().toString();
            this.restaurantId = reservation.getRestaurantId().toString();
            this.customerId = reservation.getCustomerId().toString();
            this.reservationDate = reservation.getReservationDate().toString();
            this.quantity = reservation.getQuantity();
        }
    }

    public List<ReservationDTO> toList(List<Reservation> reservationList) {
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            reservationDTOList.add(new ReservationDTO(reservation));
        }
        return reservationDTOList;
    }
}