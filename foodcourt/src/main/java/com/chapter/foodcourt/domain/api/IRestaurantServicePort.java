package com.chapter.foodcourt.domain.api;

import com.chapter.foodcourt.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);
}
