package com.chapter.foodcourt.insfrastructure.output.jpa.respository;


import com.chapter.foodcourt.insfrastructure.output.jpa.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity, Integer> {

    Optional<RestaurantEmployeeEntity> findByEmployeeId(Integer employeeId);
}
