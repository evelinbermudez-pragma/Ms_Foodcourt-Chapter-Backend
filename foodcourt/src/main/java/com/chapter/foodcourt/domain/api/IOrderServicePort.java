package com.chapter.foodcourt.domain.api;

import com.chapter.foodcourt.domain.model.Order;
import com.chapter.foodcourt.domain.model.Status;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {
    void createOrder(Order order, Integer clientId);
    Page<Order> listOrdersByStatus(Status status, Integer employeeId, int page, int size);
}
