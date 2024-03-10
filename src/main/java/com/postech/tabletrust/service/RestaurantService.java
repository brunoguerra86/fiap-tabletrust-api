package com.postech.tabletrust.service;

import com.postech.tabletrust.entity.Restaurant;

import java.util.List;
import java.util.UUID;

public interface RestaurantService {
    Restaurant newRestaurant(Restaurant restaurant);
    Restaurant findRestaurant(UUID id);
    List<Restaurant> findRestaurantsByNameAndAddressAndKitchenType(String name, String address, String kitchenType);
    Restaurant updateRestaurant(UUID id, Restaurant newRestaurant);
    boolean deleteRestaurant(UUID id);
}
