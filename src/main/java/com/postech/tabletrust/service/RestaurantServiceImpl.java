package com.postech.tabletrust.service;

import com.postech.tabletrust.entities.Restaurant;
import com.postech.tabletrust.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    @Override
    public Page<Restaurant> listRestaurants(Pageable pageable) {
        return restaurantRepository.listRestaurants(pageable);
    }
}
