package com.chapter.foodcourt.domain.spi;

import com.chapter.foodcourt.domain.model.Category;
import com.chapter.foodcourt.domain.model.Dish;
import org.springframework.data.domain.Page;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    Dish getDish(Integer id);
    Page<Dish> listDishesByRestaurant(Integer restaurantId, Category category, int page, int size);
}
