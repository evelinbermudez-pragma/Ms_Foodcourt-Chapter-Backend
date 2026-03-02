package com.chapter.foodcourt.domain.spi;

import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(Integer restaurantId);
    void saveRestaurantEmployee(RestaurantEmployee restaurant);
    RestaurantEmployee getRestaurantEmployee(Integer employeeId);
}
