package com.chapter.foodcourt.insfrastructure.output.jpa.mapper;

import com.chapter.foodcourt.domain.model.Dish;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.DishEntity;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface DishEntityMapper {

    DishEntity toDishEntity(Dish dish);
    Dish toDish(DishEntity dishEntity);
}
