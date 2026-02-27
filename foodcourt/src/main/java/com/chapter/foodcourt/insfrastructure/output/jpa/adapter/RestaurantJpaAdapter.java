package com.chapter.foodcourt.insfrastructure.output.jpa.adapter;

import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.RestaurantEntity;
import com.chapter.foodcourt.insfrastructure.output.jpa.exception.NotFoundException;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort{
    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant){
        //validacion
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
}
