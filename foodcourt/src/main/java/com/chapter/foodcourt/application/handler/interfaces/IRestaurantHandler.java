package com.chapter.foodcourt.application.handler.interfaces;

import com.chapter.foodcourt.application.dto.request.RestaurantEmployeeRequestDto;
import com.chapter.foodcourt.application.dto.request.RestaurantRequestDto;
import com.chapter.foodcourt.application.dto.response.RestaurantEmployeeResponseDto;
import com.chapter.foodcourt.application.dto.response.RestaurantResponseDto;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);
    RestaurantResponseDto getRestaurant(Integer id);
    void saveRestaurantEmployee(RestaurantEmployeeRequestDto restaurantEmployeeRequestDto);
    RestaurantEmployeeResponseDto getRestaurantEmployee(Integer employeeId);

}
