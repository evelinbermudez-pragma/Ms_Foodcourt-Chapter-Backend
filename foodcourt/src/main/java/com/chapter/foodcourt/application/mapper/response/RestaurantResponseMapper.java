package com.chapter.foodcourt.application.mapper.response;

import com.chapter.foodcourt.application.dto.response.RestaurantResponseDto;
import com.chapter.foodcourt.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel =  "spring",
       unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantResponseMapper {
        RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant);
}
