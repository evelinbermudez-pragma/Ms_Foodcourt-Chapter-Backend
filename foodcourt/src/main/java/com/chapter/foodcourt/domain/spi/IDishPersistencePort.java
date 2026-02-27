package com.chapter.foodcourt.domain.spi;

import com.chapter.foodcourt.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    Dish getDish(Integer id);
}
