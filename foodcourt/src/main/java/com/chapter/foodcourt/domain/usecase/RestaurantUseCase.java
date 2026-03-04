package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.model.User;
import com.chapter.foodcourt.domain.spi.IUserRepository;
import com.chapter.foodcourt.domain.api.IRestaurantServicePort;
import com.chapter.foodcourt.domain.exception.InvalidRoleException;
import com.chapter.foodcourt.domain.exception.*;
import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;

import static com.chapter.foodcourt.insfrastructure.configuration.Constants.*;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserRepository userRepository;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort,
                             IUserRepository userRepository) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userRepository = userRepository;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        User user = userRepository.getUserById(restaurant.getOwnerId())
                .orElseThrow(() -> new InvalidRoleException("User not found"));

        if (!user.getRoleId().equals(OWNER_ROLE_ID)) {
            throw new InvalidRoleException("The user is not owner");
        }
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public void saveRestaurantEmployee(RestaurantEmployee restaurantEmployee) {
        User user = userRepository.getUserByEmail(restaurantEmployee.getEmployeeEmail())
                .orElseThrow(() -> new InvalidRoleException("User not found"));

        Restaurant restaurant = restaurantPersistencePort
                .getRestaurant(restaurantEmployee.getRestaurantId());

        if (!user.getRoleId().equals(EMPLOYEE_ROLE_ID)) {
            throw new InvalidRoleException("The user is not employee");
        }
        if (restaurant == null) {
            throw new RestaurantNotFoundException("The restaurant doesn't exist");
        }

        restaurantEmployee.setEmployeeId(user.getId());
        restaurantPersistencePort.saveRestaurantEmployee(restaurantEmployee);
    }

    @Override
    public Restaurant getRestaurant(Integer id) {
        return restaurantPersistencePort.getRestaurant(id);
    }

    @Override
    public RestaurantEmployee getRestaurantEmployee(Integer employeeId) {
        return restaurantPersistencePort.getRestaurantOfEmployee(employeeId)
                .orElseThrow(() -> new RestaurantNotFoundException("Employee not found"));
    }

}
