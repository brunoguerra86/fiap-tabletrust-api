package com.postech.tabletrust.service;

import com.postech.tabletrust.entities.Reservation;
import com.postech.tabletrust.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RestaurantService {
    Restaurant newRestaurant(Restaurant restaurant);
    Restaurant findRestaurant(UUID id);
    List<Restaurant> listRestaurants();
    Restaurant updateRestaurant(UUID id, Restaurant newRestaurant);
    void deleteRestaurant(UUID id);
}
