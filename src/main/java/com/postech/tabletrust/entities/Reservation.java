package com.postech.tabletrust.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "Reservation")
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized

public class Reservation {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid")
    private UUID id;

    @JoinColumn(name = "id", referencedColumnName = "id")
    private Restaurant restaurant;

    @JoinColumn(name = "id", referencedColumnName = "id")
    private Customer customer;

    @NotNull(message = "A data não pode ser nula.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationDate;

    @NotNull(message = "A quantidade de lugares não pode ser nula.")
    private Integer quantity;

    public UUID getCustomerId() {
        return customer != null ? customer.getId() : null;
    }

    public UUID getRestaurantId() {
        return restaurant != null ? restaurant.getId() : null;
    }
}
