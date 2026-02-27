package com.chapter.foodcourt.insfrastructure.output.jpa.adapter;

import com.chapter.foodcourt.domain.model.Dish;
import com.chapter.foodcourt.domain.spi.IDishPersistencePort;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.DishEntity;
import com.chapter.foodcourt.insfrastructure.output.jpa.exception.NotFoundException;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.DishEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IDishRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;
        @Override
        public void saveDish(Dish dish) {
            dishRepository.save(dishEntityMapper.toDishEntity(dish));
        }
        @Override
        public Dish getDish(Integer dishId) {
            Optional<DishEntity> dish = dishRepository.findById(dishId);
            if(dish.isPresent()){
                return dishEntityMapper.toDish(dish.get());}
            throw new NotFoundException("Dish not found with id: " + dishId);
        }
}
