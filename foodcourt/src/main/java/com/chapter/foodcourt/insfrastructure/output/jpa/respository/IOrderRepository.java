package com.chapter.foodcourt.insfrastructure.output.jpa.respository;

import com.chapter.foodcourt.domain.model.Status;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Integer> {
    boolean existsByClientIdAndStatusIn(Integer clientId, List<Status> statuses);
    Page<OrderEntity> findByRestaurantIdAndStatus(Integer restaurantId, Status status, Pageable pageable);
}
