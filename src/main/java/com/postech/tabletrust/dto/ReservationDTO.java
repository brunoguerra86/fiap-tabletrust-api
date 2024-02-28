package com.postech.tabletrust.dto;

import com.postech.tabletrust.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class ReservationDTO {
    private String id;
    private String restaurantId;
    private String customerId;
    private String tableId;
    private LocalDateTime reservationDate;
    private Integer quantity;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId().toString();
        this.restaurantId = reservation.getRestaurantId().toString();
        this.customerId = reservation.getCustomerId().toString();
        this.tableId = null; //todo settar corretamente
        this.reservationDate = reservation.getReservationDate();
        this.quantity = reservation.getQuantity();

    }
}