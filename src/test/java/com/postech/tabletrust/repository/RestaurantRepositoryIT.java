package com.postech.tabletrust.repository;

import com.postech.tabletrust.entities.Restaurant;
import com.postech.tabletrust.utils.NewEntititesHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RestaurantRepositoryIT {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void shouldAllowCreateATable() {
        var totalRestaurants = restaurantRepository.count();
        assertThat(totalRestaurants).isNotNegative();
    }

    @Test
    void shouldAllowSaveRestaurant() {
        //Arrange
        Restaurant restaurant = NewEntititesHelper.createARestaurant();
        //Act
        var restaurantFoud = restaurantRepository.save(restaurant);
        //Assert
        assertThat(restaurantFoud).isInstanceOf(Restaurant.class).isNotNull();
        assertThat(restaurantFoud.getId()).isNotNull(); // Verifica que um UUID foi gerado
        assertThat(restaurantFoud.getAddress()).isEqualTo(restaurant.getAddress());
        assertThat(restaurantFoud.getName()).isEqualTo(restaurant.getName());
    }

    @Test
    void shouldFindRestaurantsByNameAndAddressAndKitchenType() {
        // Arrange
        Restaurant restaurant = NewEntititesHelper.createARestaurant();
        var restaurantFoud = restaurantRepository.save(restaurant);

        // Act - busca restaurantes usando o método do repositório
        List<Restaurant> foundRestaurants = restaurantRepository.findRestaurantsByNameAndAddressAndKitchenType(
                restaurantFoud.getName(),
                restaurantFoud.getAddress(),
                restaurantFoud.getKitchenType());

        // Assert - verifica se o restaurante correto foi encontrado
        assertFalse(foundRestaurants.isEmpty());  // ou assertThat(foundRestaurants).isNotEmpty();
        assertThat(foundRestaurants.get(0).getId()).isEqualTo(restaurant.getId());
    }
}
