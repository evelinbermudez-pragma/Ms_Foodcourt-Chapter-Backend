package com.chapter.foodcourt.application.mapper.request;

import com.chapter.foodcourt.application.dto.request.DishRequestDto;
import com.chapter.foodcourt.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishRequestMapper {
    Dish toDish(DishRequestDto dishRequestDto);
}
