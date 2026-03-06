package com.chapter.foodcourt.insfrastructure.output.jpa.respository;

import com.chapter.foodcourt.domain.model.Category;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Integer> {
    // Sin filtro de categoría
    Page<DishEntity> findByRestaurantIdAndActiveTrue(
            Integer restaurantId, Pageable pageable);

    // Con restaurante Y categoría
    Page<DishEntity> findByRestaurantIdAndCategoryAndActiveTrue(
            Integer restaurantId, Category category, Pageable pageable);


}
