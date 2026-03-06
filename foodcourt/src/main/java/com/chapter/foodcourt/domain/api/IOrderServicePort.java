package com.chapter.foodcourt.domain.api;

import com.chapter.foodcourt.domain.model.Order;

public interface IOrderServicePort {
    void createOrder(Order order, Integer clientId);
}
