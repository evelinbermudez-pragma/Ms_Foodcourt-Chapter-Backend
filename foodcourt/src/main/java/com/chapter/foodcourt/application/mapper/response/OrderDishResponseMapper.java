package com.chapter.foodcourt.application.mapper.response;

import com.chapter.foodcourt.application.dto.response.OrderDishResponseDto;
import com.chapter.foodcourt.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDishResponseMapper {

    @Mapping(source = "dishName", target = "name")
    @Mapping(source = "dishDescription", target = "description")
    @Mapping(source = "dishPrice", target = "price")
    @Mapping(source = "dishCategory", target = "category")
    @Mapping(source = "dishImageUrl", target = "imageUrl")
    OrderDishResponseDto toOrderDishResponse(OrderDish orderDish);
}
