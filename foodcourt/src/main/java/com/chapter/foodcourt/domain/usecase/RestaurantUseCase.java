package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.api.IRestaurantServicePort;
import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.chapter.foodcourt.insfrastructure.client.IUserClient;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClient userClient;
    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserClient userClient) {
        this.restaurantPersistencePort = restaurantPersistencePort;
         this.userClient = userClient;}

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        //validacion
        restaurantPersistencePort.saveRestaurant(restaurant);
    }
}
