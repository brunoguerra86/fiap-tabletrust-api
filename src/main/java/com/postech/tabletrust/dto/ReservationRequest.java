package com.postech.tabletrust.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
@AllArgsConstructor
public class ReservationRequest {
    private String id;
    private String restaurantId;
    private String customerId;
    private String reservationDate;
    private String quantity;
}