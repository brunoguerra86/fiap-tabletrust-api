package com.postech.tabletrust.interfaces;

import com.postech.tabletrust.entities.Restaurant;

import java.util.List;

public interface IRestaurantGateway {
    public Restaurant findRestaurantById(String id);

    List<Restaurant> listAllRestaurants();
}
