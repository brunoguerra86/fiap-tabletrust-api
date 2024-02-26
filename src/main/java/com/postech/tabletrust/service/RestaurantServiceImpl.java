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
        try {
            UUID.fromString(id.toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));
    }

    @Override
    public List<Restaurant> findRestaurantsByNameAndAddressAndKitchenType(String name, String address, String kitchenType) {
        return restaurantRepository.findRestaurantsByNameAndAddressAndKitchenType(name, address, kitchenType);
    }

    @Override
    public Restaurant updateRestaurant(UUID id, Restaurant newRestaurant) {
        Restaurant restaurant = findRestaurant(id);
        if (newRestaurant.getId() != null && !restaurant.getId().equals(newRestaurant.getId())) {
            throw new IllegalArgumentException("Restaurante não apresenta o ID correto");
        }
        newRestaurant.setId(id);
        restaurant = newRestaurant;
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(UUID id) {
        Restaurant restaurant = findRestaurant(id);
        restaurantRepository.delete(restaurant);
    }
}
