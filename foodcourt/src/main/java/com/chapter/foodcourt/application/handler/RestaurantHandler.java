package com.chapter.foodcourt.application.handler;

import com.chapter.foodcourt.application.dto.request.RestaurantEmployeeRequestDto;
import com.chapter.foodcourt.application.dto.request.RestaurantRequestDto;
import com.chapter.foodcourt.application.dto.response.ListRestaurantResponseDto;
import com.chapter.foodcourt.application.dto.response.RestaurantEmployeeResponseDto;
import com.chapter.foodcourt.application.dto.response.RestaurantResponseDto;
import com.chapter.foodcourt.application.handler.interfaces.IRestaurantHandler;
import com.chapter.foodcourt.application.mapper.request.RestaurantEmployeeRequestMapper;
import com.chapter.foodcourt.application.mapper.request.RestaurantRequestMapper;
import com.chapter.foodcourt.application.mapper.response.RestaurantEmployeeResponseMapper;
import com.chapter.foodcourt.application.mapper.response.RestaurantResponseMapper;
import com.chapter.foodcourt.domain.api.IRestaurantServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {
    private final IRestaurantServicePort restaurantServicePort;
    private final RestaurantRequestMapper restaurantRequestMapper;
    private final RestaurantResponseMapper restaurantResponseMapper;
    private final RestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;
    private final RestaurantEmployeeResponseMapper restaurantEmployeeResponseMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        restaurantServicePort.saveRestaurant(
                restaurantRequestMapper.toRestaurant(restaurantRequestDto)
        );
    }
    @Override
    public RestaurantResponseDto getRestaurant(Integer id) {
        return restaurantResponseMapper.toRestaurantResponseDto(restaurantServicePort.getRestaurant(id));
    }
    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeRequestDto restaurantEmployeeRequestDto) {
        restaurantServicePort.saveRestaurantEmployee(restaurantEmployeeRequestMapper.toRestaurantEmployeeModel(restaurantEmployeeRequestDto));
    }

    @Override
        public RestaurantEmployeeResponseDto getRestaurantEmployee(Integer employeeId) {
        return restaurantEmployeeResponseMapper.toRestaurantEmployeeResponseDto(restaurantServicePort.getRestaurantEmployee(employeeId));
    }
    /*
    @Override
    public Page<RestaurantResponseDto> listRestaurants(int page, int size){
        return restaurantServicePort
                .listRestaurants(page, size)
                .map(restaurantResponseMapper::toRestaurantResponseDto);
    }

     */
    @Override
    public List<ListRestaurantResponseDto> listRestaurants(int page, int size) {
        return restaurantServicePort.listRestaurants(page, size)
                .stream()
                .map(restaurantResponseMapper::toListRestaurantDto)
                .toList();
    }

}
