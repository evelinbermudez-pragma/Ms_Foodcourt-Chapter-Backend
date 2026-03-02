package com.chapter.foodcourt.insfrastructure.output.jpa.mapper;

import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEmployeeEntityMapper {

    RestaurantEmployeeEntity toRestaurantEmployeeEntity(RestaurantEmployee restaurantEmployee);
    RestaurantEmployee toRestaurantEmployeeModel(RestaurantEmployeeEntity restaurantEmployeeEntity);
}
