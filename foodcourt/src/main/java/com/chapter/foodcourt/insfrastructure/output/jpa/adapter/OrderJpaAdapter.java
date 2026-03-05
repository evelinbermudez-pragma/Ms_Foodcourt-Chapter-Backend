package com.chapter.foodcourt.insfrastructure.output.jpa.adapter;

import com.chapter.foodcourt.domain.model.Order;
import com.chapter.foodcourt.domain.model.Status;
import com.chapter.foodcourt.domain.spi.IOrderPersistencePort;
import com.chapter.foodcourt.insfrastructure.output.jpa.entity.OrderEntity;
import com.chapter.foodcourt.insfrastructure.output.jpa.exception.NotFoundException;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.OrderEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

public class OrderJpaAdapter {
    @Component
    @RequiredArgsConstructor
    public class OrderJpaAdapter implements IOrderPersistencePort {

        private final IOrderRepository orderRepository;
        private final OrderEntityMapper orderEntityMapper;

        @Override
        public void saveOrder(Order order) {
            OrderEntity entity = orderEntityMapper.toOrderEntity(order);
            if (entity.getOrderDishes() != null) {
                entity.getOrderDishes().forEach(dish -> dish.setOrder(entity));
            }
            orderRepository.save(entity);
        }

        @Override
        public Order getOrder(Integer orderId) {
           //agregar get pedido
            return orderRepository.findById(orderId)
                    .map(orderEntityMapper::toOrder)
                    .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
        }

        @Override
        public boolean hasActiveOrders(Integer clientId) {
            return orderRepository.existsByClientIdAndStatusIn(
                    clientId,
                    List.of(Status.PENDING, Status.IN_PREPARATION, Status.READY)
            );
        }
}
