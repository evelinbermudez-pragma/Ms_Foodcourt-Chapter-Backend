package com.chapter.foodcourt.insfrastructure.output.jpa.adapter;

import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.RestaurantEntity;
import com.chapter.foodcourt.insfrastructure.output.jpa.exception.ExistsPhoneException;
import com.chapter.foodcourt.insfrastructure.output.jpa.exception.ExistsRestaurantException;
import com.chapter.foodcourt.insfrastructure.output.jpa.exception.NotFoundException;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.RestaurantEmployeeEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IRestaurantEmployeeRepository;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort{

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final RestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant){
        if (restaurantRepository.findByNit(restaurant.getNit()).isPresent()) {
            throw new ExistsRestaurantException("The restaurant with NIT " + restaurant.getNit() + " already exists.");
        }
        if (restaurantRepository.findByPhone(restaurant.getPhone()).isPresent()) {
            throw new ExistsPhoneException("The restaurant with phone " + restaurant.getPhone() + " already exists.");
        }
        restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurant));
    }
    @Override
    public Restaurant getRestaurant(Integer restaurantId) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(restaurantId);
        if (restaurantEntity.isPresent()) {
            return restaurantEntityMapper.toRestaurantModel(restaurantEntity.get());
        }
        throw new NotFoundException("Restaurant not found with id: " + restaurantId);
    }
    @Override
    public void saveRestaurantEmployee(RestaurantEmployee restaurant) {
        restaurantEmployeeRepository.save(restaurantEmployeeEntityMapper.toRestaurantEmployeeEntity(restaurant));
    }

    @Override
    public Optional<RestaurantEmployee> getRestaurantOfEmployee(Integer employeeId) {
        return  restaurantEmployeeRepository.findByEmployeeId(employeeId)
                 .map(restaurantEmployeeEntityMapper::toRestaurantEmployeeModel);
    }
}
