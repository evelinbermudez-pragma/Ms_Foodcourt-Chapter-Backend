package com.chapter.foodcourt.insfrastructure.output.jpa.mapper;

import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE,
unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {
        RestaurantEntity toRestaurantEntity(Restaurant restaurant);
        Restaurant toRestaurantModel(RestaurantEntity restaurantEntity);
}
