package com.postech.tabletrust.utils;

import com.postech.tabletrust.dto.ReservationDTO;

import java.util.UUID;

public class ReservationHelper {
    public static ReservationDTO gerarReservationInsertRequest() {
        return ReservationDTO.builder()
                .id(UUID.randomUUID().toString())
                .restaurantId("2b9c1a1e-c257-4bc6-8efe-c1db33d4c52c")
                .customerId("cecad256-a3c3-4c09-833c-36586cd00f45")
                .reservationDate("2024-02-20T20:30")
                .quantity(4)
                .build();
    }
}
