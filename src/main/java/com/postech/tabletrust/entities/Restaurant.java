package com.postech.tabletrust.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String address;
    private String kitchenType; //TODO enum ou tabela de domínio
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Integer availableCapacity;

}
