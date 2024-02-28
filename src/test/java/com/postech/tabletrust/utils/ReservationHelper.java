package com.postech.tabletrust.utils;

import com.postech.tabletrust.dto.ReservationDTO;

import java.time.LocalDateTime;

public class ReservationHelper {
    public static ReservationDTO gerarReservationRequest() {
        return ReservationDTO.builder()
                .id("ae3ccc0e-106e-4445-a0f4-b700b009e018")
                .restaurantId("2b9c1a1e-c257-4bc6-8efe-c1db33d4c52c")
                .customerId("cecad256-a3c3-4c09-833c-36586cd00f45")
                .reservationDate(LocalDateTime.parse("2024-02-20 20:30"))
                .quantity(4)
                .build();
    }
}
