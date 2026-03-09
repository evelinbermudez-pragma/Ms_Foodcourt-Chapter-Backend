package com.chapter.foodcourt.domain.api;

import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;

import java.util.List;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(Integer id);
    void saveRestaurantEmployee(RestaurantEmployee restaurantEmployee);
    RestaurantEmployee getRestaurantEmployee(Integer employeeId);
   // Page<Restaurant> listRestaurants(Integer page, Integer size);
   List<Restaurant> listRestaurants(Integer page, Integer size);
}
