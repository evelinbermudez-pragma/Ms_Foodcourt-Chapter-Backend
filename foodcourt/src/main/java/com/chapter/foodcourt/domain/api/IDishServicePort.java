package com.chapter.foodcourt.domain.api;

import com.chapter.foodcourt.domain.model.Category;
import com.chapter.foodcourt.domain.model.Dish;
import org.springframework.data.domain.Page;

public interface IDishServicePort {
        void saveDish(Dish dish, Integer userId);
        Dish getDish(Integer dishId);
        void updateDish(Dish dish, Integer userId, Integer dishId);
        void toggleDish(Integer dishId, Boolean active, Integer userId);
        Page<Dish> listDishesByRestaurant(Integer restaurantId, Category categoryId, int page, int size);
}
