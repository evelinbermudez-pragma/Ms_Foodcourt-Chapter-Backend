package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.application.dto.client.response.UserResponseDto;
import com.chapter.foodcourt.domain.api.IRestaurantServicePort;
import com.chapter.foodcourt.domain.exception.DishNotFoundException;
import com.chapter.foodcourt.domain.exception.InvalidRoleException;
import com.chapter.foodcourt.domain.exception.UserIsNotOwnerException;
import com.chapter.foodcourt.domain.exception.*;
import com.chapter.foodcourt.domain.model.Dish;
import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.chapter.foodcourt.insfrastructure.client.IUserClient;

import static com.chapter.foodcourt.insfrastructure.configuration.Constants.*;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClient userClient;
    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserClient userClient) {
        this.restaurantPersistencePort = restaurantPersistencePort;
         this.userClient = userClient;}
    @Override
    public void saveRestaurant(Restaurant restaurant) {

        UserResponseDto userDto = userClient.getUser(restaurant.getOwnerId());

        if(!userDto.getRoleId().equals(OWNER_ROLE_ID)) {
            throw new InvalidRoleException("The user is not owner");
        }
        restaurantPersistencePort.saveRestaurant(restaurant);

    }

    @Override
    public Restaurant getRestaurant(Integer id) {
        return restaurantPersistencePort.getRestaurant(id);
    }

    @Override
    public void saveRestaurantEmployee(RestaurantEmployee restaurantEmployee) {
        UserResponseDto userDto = userClient.getEmployee(restaurantEmployee.getEmployeeEmail());
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(restaurantEmployee.getRestaurantId());

        if(!userDto.getRoleId().equals(EMPLOYEE_ROLE_ID)) {
            throw new InvalidRoleException("The user is not employee");
        }

        if(restaurant == null) {
            throw new RestaurantNotFoundException("The restaurant doesn't exist");
        }

        restaurantEmployee.setEmployeeId(userDto.getId());

        restaurantPersistencePort.saveRestaurantEmployee(restaurantEmployee);
    }

    @Override
    public RestaurantEmployee getRestaurantEmployee(Integer employeeId) {
        return restaurantPersistencePort.getRestaurantEmployee(employeeId);
    }

}
