package com.chapter.foodcourt.application.mapper.response;

import com.chapter.foodcourt.application.dto.response.OrderDishResponseDto;
import com.chapter.foodcourt.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDishResponseMapper {
    OrderDishResponseDto toOrderDishResponse(OrderDish orderDish);
}
