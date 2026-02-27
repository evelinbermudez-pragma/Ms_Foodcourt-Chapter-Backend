package com.chapter.foodcourt.insfrastructure.output.jpa.respository;

import com.chapter.foodcourt.insfrastructure.output.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Integer>{
    Optional<RestaurantEntity> findByNit(String nit);
    Optional<RestaurantEntity> findByPhone(String phone);
}

