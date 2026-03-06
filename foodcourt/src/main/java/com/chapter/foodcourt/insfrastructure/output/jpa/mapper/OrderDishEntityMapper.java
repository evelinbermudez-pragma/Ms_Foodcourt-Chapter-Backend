package com.chapter.foodcourt.insfrastructure.output.jpa.mapper;

import com.chapter.foodcourt.domain.model.OrderDish;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDishEntityMapper {
    OrderDishEntity toOrderDishEntity(OrderDish orderDish);
    OrderDish toOrderDish(OrderDishEntity orderDishEntity);
}
