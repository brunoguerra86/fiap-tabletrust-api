package com.postech.tabletrust.utils;

import com.postech.tabletrust.dto.ReservationDTO;
import com.postech.tabletrust.entities.FeedBack;
import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.entities.Restaurant;
import org.hibernate.type.descriptor.java.LocalTimeJavaType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class NewEntititesHelper {

    private static UUID customerID = UUID.fromString("b732236c-3c25-4290-bfe2-93ec920bcfa9");
    private static UUID restaurantID = UUID.fromString("c68b4872-6073-4dff-8199-a24c74d4c763");
    private static UUID reservationID = UUID.fromString("38f6df39-9118-4610-a435-7572648540a0");
    private static UUID feedbackID = UUID.fromString("7cad184d-6b00-4e20-bdeb-d4e224cf3bbd");

    public static ReservationDTO gerarReservationInsertRequest() {
        return ReservationDTO.builder()
                .id(UUID.randomUUID().toString())
                .restaurantId("2b9c1a1e-c257-4bc6-8efe-c1db33d4c52c")
                .customerId("cecad256-a3c3-4c09-833c-36586cd00f45")
                .reservationDate("2024-02-20T20:30")
                .quantity(4)
                .build();
    }

    //La meme reservation id
    public static FeedBack createAFeedBack(){

        return FeedBack.builder()
                .id(feedbackID)
                .comment("OTIMO")
                .restaurantId(restaurantID)
                .customerId(customerID)
                .reservationId(reservationID)
                .stars(5)
                .build();
    }

    public static Restaurant createARestaurant(){
        LocalTime open = new LocalTimeJavaType().fromString("19:00:00");
        LocalTime close = new LocalTimeJavaType().fromString("23:30:00");

        return Restaurant.builder()
                .id(restaurantID)
                .address("Fragonard")
                .kitchenType("Sopa")
                .name("Restaurante-teste")
                .openingTime(open)
                .closingTime(close)
                .availableCapacity(100)
                .build();
    }

    public static Reservation createAReservation() {
        LocalDateTime in3HoursAgo = LocalDateTime.now().minusHours(3);

        return Reservation.builder()
                .id(reservationID)
                .reservationDate(in3HoursAgo)
                .restaurantId(restaurantID)
                .customerId(customerID)
                .quantity(4)
                .approved(true)
                .build();
    }
}
