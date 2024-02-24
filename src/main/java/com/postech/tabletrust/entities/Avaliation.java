package com.postech.tabletrust.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avaliation")
@Jacksonized
public class Avaliation {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customerID", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurantID", referencedColumnName = "id")
    private Restaurant restaurant;

    //@OneToOne
    @JoinColumn(name = "reservationID", referencedColumnName = "id")
    private Reservation reservation;

    @NotNull
    @Min(1)  // O mínimo é 1
    @Max(5)  // O máximo é 5
    private Integer stars;
}
