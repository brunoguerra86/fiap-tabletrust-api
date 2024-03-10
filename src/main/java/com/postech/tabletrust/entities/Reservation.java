package com.postech.tabletrust.entities;

import com.postech.tabletrust.dto.ReservationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @OneToOne
    @JoinColumn (name = "restaurant_id")
    private Restaurant restaurant;
    private UUID customerId;
    private LocalDateTime reservationDate;
    private Integer quantity;
    private Boolean approved;

    public Reservation(ReservationDTO reservationDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(reservationDTO.getReservationDate(), formatter);
        this.id = reservationDTO.getId() == null ? this.id : UUID.fromString(reservationDTO.getId());
        this.restaurant = new Restaurant(UUID.fromString(reservationDTO.getRestaurantId()));
        this.customerId = UUID.fromString(reservationDTO.getCustomerId());
        this.reservationDate = dateTime;
        this.quantity = reservationDTO.getQuantity();
        this.approved = reservationDTO.getApproved();
    }
}
