package com.postech.tabletrust.service;

import com.postech.tabletrust.entities.Restaurant;
import com.postech.tabletrust.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant newRestaurant(Restaurant restaurant) {
        restaurant.setId(UUID.randomUUID());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant findRestaurant(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));
    }

    @Override
    public List<Restaurant> findRestaurantsByNameAndAddressAndKitchenType(String name, String address, String kitchenType) {
        return restaurantRepository.findRestaurantsByNameAndAddressAndKitchenType(name, address, kitchenType);
    }

    @Override
    public Restaurant updateRestaurant(UUID id, Restaurant newRestaurant) { //TODO refacto avec DTO pour validation
        newRestaurant.setId(id);
        return restaurantRepository.save(newRestaurant);
    }

    @Override
    public boolean deleteRestaurant(UUID id) {
        restaurantRepository.deleteById(id);
        return true;
    }
}
