package com.chapter.foodcourt.insfrastructure.output.jpa.mapper;

import com.chapter.foodcourt.domain.model.Order;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {
    OrderEntity toOrderEntity(Order order);
    Order toOrder(OrderEntity orderEntity);
}