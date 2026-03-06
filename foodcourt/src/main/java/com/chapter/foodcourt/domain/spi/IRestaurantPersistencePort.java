package com.chapter.foodcourt.domain.spi;

import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(Integer restaurantId);
    void saveRestaurantEmployee(RestaurantEmployee restaurant);
    Optional<RestaurantEmployee> getRestaurantOfEmployee(Integer employeeId);
    Page<Restaurant> listRestaurants(Integer page, Integer size);

}
