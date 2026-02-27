package com.chapter.foodcourt.domain.spi;

import com.chapter.foodcourt.domain.model.Restaurant;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(Integer restaurantId);
}
