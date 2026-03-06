package com.chapter.foodcourt.domain.spi;

import com.chapter.foodcourt.domain.model.Order;
import com.chapter.foodcourt.domain.model.Status;
import org.springframework.data.domain.Page;

public interface IOrderPersistencePort {
    void saveOrder(Order order);
    Order getOrder(Integer orderId);
    boolean hasActiveOrders(Integer clientId);
    Page<Order> listOrdersByStatus(Status status, Integer restaurantId, int page, int size);
}
