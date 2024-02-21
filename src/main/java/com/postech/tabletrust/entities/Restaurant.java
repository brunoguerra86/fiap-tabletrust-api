package com.postech.tabletrust.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class Restaurant {
    @Id
    @GenericGenerator(name = "uuid")
    private UUID id;

    @NotEmpty(message = "[name] não pode estar vazio")
    private String name;

    @NotEmpty(message = "[address] não pode estar vazio")
    private String address;

    @NotEmpty(message = "[kitchenType] não pode estar vazio")
    private String kitchenType; //TODO enum ou tabela de domínio

    @CreationTimestamp
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime openingTime;

    @CreationTimestamp
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime closingTime;

    @NotEmpty(message = "[availableCapacity] não pode estar vazio")
    private Integer availableCapacity;

}
