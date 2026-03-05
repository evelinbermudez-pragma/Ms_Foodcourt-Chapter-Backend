package com.chapter.foodcourt.application.handler.interfaces;

import com.chapter.foodcourt.application.dto.request.DishRequestDto;
import com.chapter.foodcourt.application.dto.request.UpdateDishRequestDto;
import com.chapter.foodcourt.application.dto.response.DishResponseDto;
import com.chapter.foodcourt.domain.model.Category;
import org.springframework.data.domain.Page;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto, Integer userId);
    DishResponseDto getDish(Integer dishId);
    void updateDish (UpdateDishRequestDto updateDishRequestDto, Integer userId, Integer dishId);
    void toggleDish(Integer dishId, Boolean active, Integer userId);
    Page<DishResponseDto> listDishesByRestaurant(Integer restaurantId, Category category, int page, int size);
}
