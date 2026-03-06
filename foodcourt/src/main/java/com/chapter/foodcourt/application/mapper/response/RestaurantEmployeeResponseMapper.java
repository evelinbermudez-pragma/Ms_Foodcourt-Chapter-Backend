package com.chapter.foodcourt.application.mapper.response;

import com.chapter.foodcourt.application.dto.response.RestaurantEmployeeResponseDto;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEmployeeResponseMapper {
    RestaurantEmployeeResponseDto toRestaurantEmployeeResponseDto(RestaurantEmployee restaurantEmployee);
}
