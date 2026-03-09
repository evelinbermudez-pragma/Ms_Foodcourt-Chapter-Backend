package com.chapter.foodcourt.domain.api;

import com.chapter.foodcourt.domain.model.Order;
import com.chapter.foodcourt.domain.model.Status;
import org.springframework.data.domain.Page;

public interface IOrderServicePort {
    Page<Order> listOrdersByStatus(Status status, int page, int size);
    void assignOrderAndChangeStatus(Integer orderId);
    void notifyOrderReady(Integer orderId);
    void deliverOrder(Integer orderId, String pin);
    void cancelOrder(Integer orderId);
    void createOrder(Order order);
}
