package com.chapter.foodcourt.application.handler.interfaces;

import com.chapter.foodcourt.application.dto.request.DishRequestDto;
import com.chapter.foodcourt.application.dto.request.UpdateDishRequestDto;
import com.chapter.foodcourt.application.dto.response.DishResponseDto;
import com.chapter.foodcourt.domain.model.Dish;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto, Integer userId);
    DishResponseDto getDish(Integer dishId);
    void updateDish (UpdateDishRequestDto updateDishRequestDto, Integer userId, Integer dishId);
}
