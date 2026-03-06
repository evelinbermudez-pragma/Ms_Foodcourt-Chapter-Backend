package com.chapter.foodcourt.application.mapper.request;

import com.chapter.foodcourt.application.dto.request.OrderDishRequestDto;
import com.chapter.foodcourt.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDishRequestMapper {
    OrderDish toOrderDish(OrderDishRequestDto dto);
}
