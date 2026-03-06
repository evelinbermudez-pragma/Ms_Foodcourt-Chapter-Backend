package com.chapter.foodcourt.insfrastructure.output.jpa.adapter;

import com.chapter.foodcourt.domain.model.Category;
import com.chapter.foodcourt.domain.model.Dish;
import com.chapter.foodcourt.domain.spi.IDishPersistencePort;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.DishEntity;
import com.chapter.foodcourt.insfrastructure.output.jpa.exception.NotFoundException;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.DishEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    @Override
    public Page<Dish> listDishesByRestaurant(Integer restaurantId, Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<DishEntity> entities;

        if (category!= null) {
            // filtra por restaurante Y categoría
            entities = dishRepository.findByRestaurantIdAndCategoryAndActiveTrue(
                    restaurantId, category, pageable);
        } else {
            // solo filtra por restaurante
            entities = dishRepository.findByRestaurantIdAndActiveTrue(
                    restaurantId, pageable);
        }

        return entities.map(dishEntityMapper::toDish);
    }
}
