package com.chapter.foodcourt.application.handler;

import com.chapter.foodcourt.application.dto.request.RestaurantRequestDto;
import com.chapter.foodcourt.application.handler.interfaces.IRestaurantHandler;
import com.chapter.foodcourt.application.mapper.request.RestaurantRequestMapper;
import com.chapter.foodcourt.application.mapper.response.RestaurantResponseMapper;
import com.chapter.foodcourt.domain.api.IRestaurantServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {
    private final IRestaurantServicePort restaurantServicePort;
    private final RestaurantRequestMapper restaurantRequestMapper;
    private final RestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        restaurantServicePort.saveRestaurant(
                restaurantRequestMapper.toRestaurant(restaurantRequestDto)
        );
    }
}
