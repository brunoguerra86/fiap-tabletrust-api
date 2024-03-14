package com.postech.tabletrust.gateways;

import com.postech.tabletrust.entity.Restaurant;
import com.postech.tabletrust.exception.NotFoundException;
import com.postech.tabletrust.interfaces.IRestaurantGateway;
import com.postech.tabletrust.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RestaurantGateway implements IRestaurantGateway {
    private final RestaurantRepository restaurantRepository;

    public RestaurantGateway(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant findRestaurantById(String strId) {
        UUID uuid = UUID.fromString(strId);
        return restaurantRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Restaurante não encontrado"));
    }

    @Override
    public List<Restaurant> listAllRestaurants() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        return restaurantList;
    }
}
