package com.chapter.foodcourt.domain.api;

import com.chapter.foodcourt.domain.model.Dish;

public interface IDishServicePort {
        void saveDish(Dish dish, Integer userId);
        Dish getDish(Integer dishId);
        void updateDish(Dish dish, Integer userId, Integer dishId);
        void toggleDish(Integer dishId, Boolean active, Integer userId);
}
