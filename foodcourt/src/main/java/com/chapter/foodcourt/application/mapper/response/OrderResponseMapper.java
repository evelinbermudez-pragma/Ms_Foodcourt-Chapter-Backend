package com.chapter.foodcourt.application.mapper.response;

import com.chapter.foodcourt.application.dto.response.OrderResponseDto;
import com.chapter.foodcourt.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {OrderDishResponseMapper.class},
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderResponseMapper {
    OrderResponseDto toOrderResponseDto(Order order);
}
