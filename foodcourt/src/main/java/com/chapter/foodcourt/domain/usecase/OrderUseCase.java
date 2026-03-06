package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.api.IOrderServicePort;
import com.chapter.foodcourt.domain.exception.ClientHasActiveOrderException;
import com.chapter.foodcourt.domain.exception.DishNotFromRestaurantException;
import com.chapter.foodcourt.domain.exception.OrderNotPendingException;
import com.chapter.foodcourt.domain.model.*;
import com.chapter.foodcourt.domain.spi.IDishPersistencePort;
import com.chapter.foodcourt.domain.spi.IOrderPersistencePort;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
         this.restaurantPersistencePort = restaurantPersistencePort;
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

    @Override
    public Page<Order> listOrdersByStatus(Status status, Integer employeeId, int page, int size) {
        // busca el restaurante del empleado
        RestaurantEmployee restaurantEmployee = restaurantPersistencePort
                .getRestaurantOfEmployee(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not assigned to any restaurant"));

        return orderPersistencePort.listOrdersByStatus(
                status,
                restaurantEmployee.getRestaurantId(),
                page,
                size
        );
    }
    @Override
    public void assignOrderAndChangeStatus(Integer orderId, Integer employeeId) {
        Order order = orderPersistencePort.getOrder(orderId);

        // Solo se pueden asignar pedidos en PENDING
        if (!order.getStatus().equals(Status.PENDING)) {
            throw new OrderNotPendingException("Order is not in PENDING status");
        }

        order.setEmployeeId(employeeId);
        order.setStatus(Status.IN_PREPARATION);
        orderPersistencePort.saveOrder(order);
    }
}
