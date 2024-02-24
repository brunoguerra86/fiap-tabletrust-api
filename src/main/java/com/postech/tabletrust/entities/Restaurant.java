package com.postech.tabletrust.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Restaurant")
@Jacksonized
public class Restaurant {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid")
    private UUID id;

    @NotEmpty(message = "[name] não pode estar vazio")
    private String name;

    @NotEmpty(message = "[address] não pode estar vazio")
    private String address;

    @Enumerated(EnumType.STRING) // Usar EnumType.STRING para corresponder ao tipo ENUM do PostgreSQL
    @NotEmpty(message = "[kitchenType] não pode estar vazio")
    private KitchenType kitchenType;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime closeTime;

    @NotEmpty(message = "[capacity] não pode estar vazio")
    private Integer capacity;

    private Integer stars;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
