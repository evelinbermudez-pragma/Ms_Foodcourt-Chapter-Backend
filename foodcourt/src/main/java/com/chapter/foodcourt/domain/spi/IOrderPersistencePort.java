package com.chapter.foodcourt.domain.spi;

import com.chapter.foodcourt.domain.model.Order;

public interface IOrderPersistencePort {
    void saveOrder(Order order);
    Order getOrder(Integer orderId);
    boolean hasActiveOrders(Integer clientId);
}
