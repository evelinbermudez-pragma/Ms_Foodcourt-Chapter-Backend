package com.chapter.foodcourt.application.mapper.response;

import com.chapter.foodcourt.application.dto.response.DishResponseDto;
import com.chapter.foodcourt.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel =  "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishResponseMapper {
    DishResponseDto toDishResponseDto(Dish dish);
}
