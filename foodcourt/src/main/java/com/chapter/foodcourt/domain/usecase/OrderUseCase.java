package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.api.IOrderServicePort;
import com.chapter.foodcourt.domain.exception.ClientHasActiveOrderException;
import com.chapter.foodcourt.domain.exception.DishNotFromRestaurantException;
import com.chapter.foodcourt.domain.model.Dish;
import com.chapter.foodcourt.domain.model.Order;
import com.chapter.foodcourt.domain.model.OrderDish;
import com.chapter.foodcourt.domain.model.Status;
import com.chapter.foodcourt.domain.spi.IDishPersistencePort;
import com.chapter.foodcourt.domain.spi.IOrderPersistencePort;

import java.time.LocalDateTime;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
    }
    @Override
    public void createOrder(Order order, Integer clientId) {

        if (orderPersistencePort.hasActiveOrders(clientId)) {
            throw new ClientHasActiveOrderException("You already have an active order");
        }
        for (OrderDish orderDish : order.getOrderDishes()) {
            Dish dish = dishPersistencePort.getDish(orderDish.getDishId());
            if (!dish.getRestaurantId().equals(order.getRestaurantId())) {
                throw new DishNotFromRestaurantException("Dish does not belong to this restaurant");
            }
        }
        order.setClientId(clientId);
        order.setStatus(Status.PENDING);
        order.setDate(LocalDateTime.now());
        orderPersistencePort.saveOrder(order);
    }
}
