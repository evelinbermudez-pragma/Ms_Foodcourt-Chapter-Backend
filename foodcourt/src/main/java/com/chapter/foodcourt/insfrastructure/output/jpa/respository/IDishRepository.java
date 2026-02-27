package com.chapter.foodcourt.insfrastructure.output.jpa.respository;

import com.chapter.foodcourt.insfrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Integer> {
    //meh meh meh
}
