package com.postech.tabletrust.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.postech.tabletrust.entity.Restaurant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class RestaurantDTO {

    private UUID id;

    @NotEmpty(message = "[name] não pode estar vazio")
    private String name;

    @NotEmpty(message = "[address] não pode estar vazio")
    private String address;

    @NotEmpty(message = "[kitchenType] não pode estar vazio")
    private String kitchenType; //TODO enum ou tabela de domínio

    //@CreationTimestamp
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime openingTime;

    //@CreationTimestamp corrigir
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime closingTime;

    @NotNull(message = "[availableCapacity] não pode ser nula")
    private Integer availableCapacity;

    public RestaurantDTO() {
    }
    public RestaurantDTO(Restaurant restaurant) {
        if (restaurant != null) {
            this.id = restaurant.getId();
            this.name = restaurant.getName().toString();
            this.address = restaurant.getAddress().toString();
            this.kitchenType = restaurant.getKitchenType().toString();
            this.openingTime = restaurant.getOpeningTime();
            this.closingTime = restaurant.getClosingTime();
            this.availableCapacity = restaurant.getAvailableCapacity();
        }
    }

    public List<RestaurantDTO> toList(List<Restaurant> restaurantList) {
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        for (Restaurant restaurant : restaurantList) {
            restaurantDTOList.add(new RestaurantDTO(restaurant));
        }
        return restaurantDTOList;
    }
}
