package com.chapter.foodcourt.application.mapper.request;

import com.chapter.foodcourt.application.dto.request.RestaurantEmployeeRequestDto;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEmployeeRequestMapper {
    RestaurantEmployee toRestaurantEmployeeModel(RestaurantEmployeeRequestDto restaurantEmployeeRequestDto);
}
