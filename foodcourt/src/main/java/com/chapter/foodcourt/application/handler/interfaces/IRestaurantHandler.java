package com.chapter.foodcourt.application.handler.interfaces;

import com.chapter.foodcourt.application.dto.request.RestaurantEmployeeRequestDto;
import com.chapter.foodcourt.application.dto.request.RestaurantRequestDto;
import com.chapter.foodcourt.application.dto.response.ListRestaurantResponseDto;
import com.chapter.foodcourt.application.dto.response.RestaurantEmployeeResponseDto;
import com.chapter.foodcourt.application.dto.response.RestaurantResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);
    RestaurantResponseDto getRestaurant(Integer id);
    void saveRestaurantEmployee(RestaurantEmployeeRequestDto restaurantEmployeeRequestDto);
    RestaurantEmployeeResponseDto getRestaurantEmployee(Integer employeeId);
    //Page<RestaurantResponseDto> listRestaurants(int page, int size);
    List<ListRestaurantResponseDto> listRestaurants(int page, int size);
}
