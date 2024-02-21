package com.postech.tabletrust.service;

import com.postech.tabletrust.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    Page<Restaurant> listRestaurants(Pageable pageable);
}
